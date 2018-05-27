package ru.capjack.ktjs.app.display.button

import ru.capjack.ktjs.app.sound.Sound
import ru.capjack.ktjs.app.sound.SoundFlow
import ru.capjack.ktjs.common.Cancelable
import ru.capjack.ktjs.common.Destroyable
import ru.capjack.ktjs.common.ProcedureGroup
import ru.capjack.ktjs.common.observable
import ru.capjack.ktjs.wrapper.pixi.Container
import ru.capjack.ktjs.wrapper.pixi.DisplayObject


class ButtonControlImp(
    private var target: DisplayObject
) : ButtonControl, Destroyable {

    private var sound: Sound? = null
    private var soundFlow: SoundFlow? = null
    private var volume: Double = 1.0

    override var data: Any? = null
    override var state = ButtonState.DISABLED
    override var enabled by observable(false, ::processChangeEnabled)

    private val refOnPointerOver = ::onPointerOver
    private val refOnPointerOut = ::onPointerOut
    private val refOnPointerDown = ::onPointerDown
    private val refOnPointerUp = ::onPointerUp
    private val refOnPointerUpOutside = ::handlePointerUpOutside

    private val pressHandlers = ProcedureGroup()
    private val stateHandlers = ProcedureGroup()

    private var pointerFocus: Boolean = false
    private var pointerActive: Boolean = false

    init {
        if (target is Container) {
            (target as Container).interactiveChildren = true
        }
        target.on("pointerover", refOnPointerOver)
        target.on("pointerout", refOnPointerOut)
    }

    override fun destroy() {
        disable()
        target.off("pointerover", refOnPointerOver)
        target.off("pointerout", refOnPointerOut)
        pressHandlers.clear()
        stateHandlers.clear()
    }

    override fun press() {
        if (enabled) {
            pressHandlers.invoke()
        }
        if (sound != null) {
            soundFlow = sound?.play()
            soundFlow?.volume = volume
        }
    }

    override fun onPress(handler: () -> Unit): Cancelable {
        return pressHandlers.add(handler)
    }

    override fun onState(handler: (new: ButtonState, old: ButtonState) -> Unit): Cancelable {
        return stateHandlers.add(handler)
    }

    override fun setSound(sound: Sound, volume: Double) {
        soundFlow?.stop()
        this.volume = volume
        this.sound = sound
    }

    private fun processChangeEnabled() {
        target.interactive = enabled

        if (enabled) {
            target.cursor = "pointer"

            target.on("pointerdown", refOnPointerDown)
            target.on("pointerup", refOnPointerUp)
            target.on("pointerupoutside", refOnPointerUpOutside)

            setState(if (pointerFocus) ButtonState.FOCUS else ButtonState.IDLE)
        } else {
            target.cursor = "default"

            pointerActive = false
            target.off("pointerdown", refOnPointerDown)
            target.off("pointerup", refOnPointerUp)
            target.off("pointerupoutside", refOnPointerUpOutside)

            setState(ButtonState.DISABLED)
        }
    }

    private fun onPointerOver() {
        pointerFocus = true
        if (enabled) {
            setState(if (pointerActive) ButtonState.ACTIVE else ButtonState.FOCUS)
        }
    }

    private fun onPointerOut() {
        pointerFocus = false
        if (enabled) {
            setState(if (pointerActive) ButtonState.FOCUS else ButtonState.IDLE)
        }
    }

    private fun onPointerDown() {
        if (enabled) {
            pointerActive = true
            pointerFocus = true
            setState(ButtonState.ACTIVE)
        }
    }

    private fun onPointerUp() {
        if (enabled) {
            setState(if (pointerFocus) ButtonState.FOCUS else ButtonState.IDLE)
            if (pointerActive) {
                pointerActive = false
                if (pointerFocus) {
                    press()
                }
            }
        }
    }

    private fun handlePointerUpOutside() {
        pointerFocus = false
        if (enabled) {
            onPointerUp()
        }
    }

    private fun setState(newState: ButtonState) {
        val oldState = state
        if (oldState != newState) {
            state = newState
            stateHandlers.invoke(newState, oldState)
        }
    }

}
package ru.capjack.ktjs.app.display.button

import ru.capjack.ktjs.common.Cancelable
import ru.capjack.ktjs.common.Delegates.observable
import ru.capjack.ktjs.common.Destroyable
import ru.capjack.ktjs.common.ProcedureGroup
import ru.capjack.ktjs.wrapper.pixi.Container
import ru.capjack.ktjs.wrapper.pixi.DisplayObject
import ru.capjack.ktjs.wrapper.pixi.interaction.InteractionEventType


class ButtonControlImp() : ButtonControl, Destroyable {
	
	private var target: DisplayObject? = null
	
	override var state = ButtonState.DISABLED
	override var enabled by observable(false, ::processChangeEnabled)
	
	private val refOnPointerOver = ::onPointerOver
	private val refOnPointerOut = ::onPointerOut
	private val refOnPointerDown = ::onPointerDown
	private val refOnPointerUp = ::onPointerUp
	private val refOnPointerUpOutside = ::onPointerUpOutside
	
	private val pressHandlers = ProcedureGroup()
	private val stateHandlers = ProcedureGroup()
	
	private var pointerFocus: Boolean = false
	private var pointerActive: Boolean = false
	
	constructor(target: DisplayObject) : this() {
		setTarget(target)
	}
	
	override fun setTarget(value: DisplayObject?) {
		if (target != value) {
			
			val e = enabled
			disable()
			
			target?.apply {
				off(InteractionEventType.POINTER_OVER, refOnPointerOver)
				off(InteractionEventType.POINTER_OUT, refOnPointerOut)
			}
			
			target = value
			
			target?.apply {
				if (this is Container) interactiveChildren = true
				
				on(InteractionEventType.POINTER_OVER, refOnPointerOver)
				on(InteractionEventType.POINTER_OUT, refOnPointerOut)
			}
			
			enabled = e
		}
	}
	
	override fun destroy() {
		target = null
		pressHandlers.clear()
		stateHandlers.clear()
	}
	
	override fun press() {
		if (enabled) {
			pressHandlers.invoke()
		}
	}
	
	override fun onPress(handler: () -> Unit): Cancelable {
		return pressHandlers.add(handler)
	}
	
	override fun onState(handler: (new: ButtonState, old: ButtonState) -> Unit): Cancelable {
		return stateHandlers.add(handler)
	}
	
	override fun onState(handler: (ButtonState) -> Unit): Cancelable {
		return stateHandlers.add(handler)
	}
	
	private fun processChangeEnabled() {
		target?.interactive = enabled
		
		if (enabled) {
			target?.apply {
				cursor = "pointer"
				on(InteractionEventType.POINTER_DOWN, refOnPointerDown)
				on(InteractionEventType.POINTER_UP, refOnPointerUp)
				on(InteractionEventType.POINTER_UP_OUTSIDE, refOnPointerUpOutside)
			}
			
			setState(if (pointerFocus) ButtonState.FOCUS else ButtonState.IDLE)
		}
		else {
			target?.apply {
				cursor = "default"
				off(InteractionEventType.POINTER_DOWN, refOnPointerDown)
				off(InteractionEventType.POINTER_UP, refOnPointerUp)
				off(InteractionEventType.POINTER_UP_OUTSIDE, refOnPointerUpOutside)
			}
			
			pointerActive = false
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
			setState(ButtonState.ACTIVE)
		}
	}
	
	private fun onPointerUp() {
		if (enabled) {
			setState(if (pointerFocus) ButtonState.FOCUS else ButtonState.IDLE)
			if (pointerActive) {
				pointerActive = false
				press()
			}
		}
	}
	
	private fun onPointerUpOutside() {
		pointerFocus = false
		if (enabled) {
			pointerActive = false
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
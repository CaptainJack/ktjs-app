package ru.capjack.ktjs.app.display.button

import ru.capjack.ktjs.app.sound.Sound
import ru.capjack.ktjs.common.Able
import ru.capjack.ktjs.common.Cancelable
import ru.capjack.ktjs.common.Destroyable

interface ButtonControl : Able, Destroyable {
    val state: ButtonState

    var data: Any?

    fun press()

    fun onPress(handler: () -> Unit): Cancelable
    
    fun onState(handler: (ButtonState) -> Unit): Cancelable
    
    fun onState(handler: (new: ButtonState, old: ButtonState) -> Unit): Cancelable

    fun setSound(sound: Sound, volume: Double = 1.0)
}
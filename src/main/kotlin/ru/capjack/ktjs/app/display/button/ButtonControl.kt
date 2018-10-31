package ru.capjack.ktjs.app.display.button

import ru.capjack.ktjs.common.Able
import ru.capjack.ktjs.common.Cancelable
import ru.capjack.ktjs.common.Destroyable
import ru.capjack.ktjs.wrapper.pixi.DisplayObject

interface ButtonControl : Able, Destroyable {
	val state: ButtonState
	
	fun setTarget(value: DisplayObject?)
	
	fun press()
	
	fun onPress(handler: () -> Unit): Cancelable
	
	fun onState(handler: (ButtonState) -> Unit): Cancelable
	
	fun onState(handler: (new: ButtonState, old: ButtonState) -> Unit): Cancelable
}
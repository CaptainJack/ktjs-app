package ru.capjack.ktjs.app.display.button

import ru.capjack.ktjs.wrapper.pixi.Text
import ru.capjack.ktjs.wrapper.pixi.TextStyle


class TextDetail(text: String, styles: ButtonStateValues<TextStyle>) : StatedDetail<TextStyle>(styles) {
	override val display = Text(text, styles[ButtonState.DISABLED])
	
	var text: String
		get() = display.text
		set(value) {
			display.text = value
		}
	
	override fun setStateValue(value: TextStyle) {
		display.style = value
	}
}
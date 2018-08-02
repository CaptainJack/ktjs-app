package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.app.display.misc.TextStyle
import ru.capjack.ktjs.wrapper.pixi.Text as PixiText

class Text(
	style: TextStyle,
	value: String = ""

) : Tail(false) {
	
	override val display = PixiText(
		style.transform(value),
		style.pixi
	)
	
	var style = style
		set(value) {
			if (field != value) {
				field = value
				display.style = value.pixi
				processChangedValue()
			}
		}
	
	var value: String
		get() = display.text
		set(value) {
			val v = style.transform(value)
			if (v != display.text) {
				display.text = v
				processChangedValue()
			}
		}
	
	init {
		processChangedValue()
		//TODO Почему то иногда view.width содержит неверное значение
	}
	
	private fun processChangedValue() {
		updateContentSizeByDisplay()
	}
}
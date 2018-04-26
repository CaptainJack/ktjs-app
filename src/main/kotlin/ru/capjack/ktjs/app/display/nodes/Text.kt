package ru.capjack.ktjs.app.display.nodes

import ru.capjack.ktjs.app.display.misc.TextStyle
import ru.capjack.ktjs.common.invokeDelayed
import ru.capjack.ktjs.wrapper.pixi.Text as PixiText

class Text(style: TextStyle, value: String = "") : NodeOfContainer<PixiText>(PixiText(style.transform(value), style.pixi)) {
	var style = style
		set(value) {
			if (field != value) {
				field = value
				view.style = value.pixi
				processValueChanged()
			}
		}
	
	var value: String
		get() = view.text
		set(value) {
			val v = style.transform(value)
			if (v != view.text) {
				view.text = v
				processValueChanged()
			}
		}
	
	init {
		//TODO Почему то иногда view.width содержит неверное значение
		invokeDelayed(::specifySizeByView)
	}
	
	override fun processSizeChanged() {
	}
	
	private fun processValueChanged() {
		specifySizeByView()
	}
}
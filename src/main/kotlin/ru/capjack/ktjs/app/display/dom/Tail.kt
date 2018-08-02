package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.app.display.dom.traits.SizeRules
import ru.capjack.ktjs.wrapper.pixi.setSize

abstract class Tail(deformable: Boolean) : NodeOfContainer() {
	
	init {
		if (deformable) {
			size.onChange { display.setSize(size) }
		}
		sizeRule = SizeRules.STRETCHING
	}
	
	protected fun updateContentSizeByDisplay() {
		_contentSize.set(
			(display.width / display.scale.x).toInt(),
			(display.height / display.scale.y).toInt()
		)
	}
}
package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.app.display.dom.traits.SizeRules
import ru.capjack.ktjs.wrapper.pixi.setSize

abstract class Tail(protected val deformable: Boolean) : Node() {
	
	init {
		if (deformable) {
			size.onChange { -> display.setSize(innerSize) }
		}
		sizeRule = SizeRules.STRETCHING
	}
	
	protected fun updateContentSizeByDisplay() {
		mutableContentSize.set(
			(display.width / display.scale.x).toInt(),
			(display.height / display.scale.y).toInt()
		)
	}
}
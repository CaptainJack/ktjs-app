package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.MutableChangeableAxialValuesImpl

internal class SizeImp : Dimension, MutableChangeableAxialValuesImpl<Int>(0, 0) {
	override var x: Int
		get() = super.x
		set(value) {
			super.x = value.coerceAtLeast(0)
		}
	
	override var y: Int
		get() = super.y
		set(value) {
			super.y = value.coerceAtLeast(0)
		}
	
	override fun set(x: Int, y: Int) {
		super.set(x.coerceAtLeast(0), y.coerceAtLeast(0))
	}
}
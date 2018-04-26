package ru.capjack.ktjs.app.display.misc

import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.ChangeableAxialValues

abstract class AdjustableChangeableAxialValues<T>(horizontal: T, vertical: T) : ChangeableAxialValues<T>(horizontal, vertical) {
	override var horizontal: T
		get() = super.horizontal
		set(value) {
			super.horizontal = adjust(Axis.HORIZONTAL, value)
		}
	
	override var vertical: T
		get() = super.vertical
		set(value) {
			super.vertical = adjust(Axis.VERTICAL, value)
		}
	
	override fun set(horizontal: T, vertical: T) {
		super.set(adjust(Axis.HORIZONTAL, horizontal), adjust(Axis.VERTICAL, vertical))
	}
	
	abstract fun adjust(axis: Axis, value: T): T
}
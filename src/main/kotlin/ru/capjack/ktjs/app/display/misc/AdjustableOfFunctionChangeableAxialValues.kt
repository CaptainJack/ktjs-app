package ru.capjack.ktjs.app.display.misc

import ru.capjack.ktjs.common.geom.Axis

open class AdjustableOfFunctionChangeableAxialValues<T>(
	private val adjuster: (axis: Axis, value: T) -> T,
	horizontal: T,
	vertical: T = horizontal

) : AdjustableChangeableAxialValues<T>(horizontal, vertical) {
	
	override fun adjust(axis: Axis, value: T): T {
		return adjuster(axis, value)
	}
}
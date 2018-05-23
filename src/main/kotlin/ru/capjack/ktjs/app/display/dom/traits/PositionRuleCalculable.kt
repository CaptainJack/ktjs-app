package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxialValues

abstract class PositionRuleCalculable : PositionRule {
	override fun isApplicable(type: SpaceType) = true
	
	override fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, region: AxialValues<Int>) {
		target.set(calculate(space, region, Axis.X), calculate(space, region, Axis.Y))
	}
	
	override fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, region: AxialValues<Int>, axis: Axis) {
		target[axis] = calculate(space, region, axis)
	}
	
	abstract fun calculate(space: AxialValues<Int>, region: AxialValues<Int>, axis: Axis): Int
}
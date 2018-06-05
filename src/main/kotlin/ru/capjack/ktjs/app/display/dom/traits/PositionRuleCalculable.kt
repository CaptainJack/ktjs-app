package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxial

abstract class PositionRuleCalculable : PositionRule {
	override fun isApplicable(type: SpaceType) = true
	
	override fun isApplicable(type: SpaceType, axis: Axis) = true
	
	override fun apply(target: MutableAxial<Int>, space: Axial<Int>, region: Axial<Int>) {
		target.set(calculate(space, region, Axis.X), calculate(space, region, Axis.Y))
	}
	
	override fun apply(target: MutableAxial<Int>, space: Axial<Int>, region: Axial<Int>, axis: Axis) {
		target[axis] = calculate(space, region, axis)
	}
	
	abstract fun calculate(space: Axial<Int>, region: Axial<Int>, axis: Axis): Int
}
package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxial

abstract class PositionRuleCalculable : PositionRule {
	override fun apply(target: MutableAxial<Int>, position: Axial<Int>, space: Axial<Int>, region: Axial<Int>) {
		target.set(
			calculate(position, space, region, Axis.X),
			calculate(position, space, region, Axis.Y)
		)
	}
	
	override fun apply(target: MutableAxial<Int>, position: Axial<Int>, space: Axial<Int>, region: Axial<Int>, axis: Axis) {
		target[axis] = calculate(position, space, region, axis)
	}
	
	abstract fun calculate(position: Axial<Int>, space: Axial<Int>, region: Axial<Int>, axis: Axis): Int
}
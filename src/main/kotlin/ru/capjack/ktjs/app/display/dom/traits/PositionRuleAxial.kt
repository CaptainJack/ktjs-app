package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxialValues

class PositionRuleAxial(x: PositionRule, y: PositionRule) : PositionRule, DimensionRuleAxial<PositionRule>(x, y) {
	override fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, region: AxialValues<Int>) {
		Axis.forEach { get(it).apply(target, space, region, it) }
	}
	
	override fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, region: AxialValues<Int>, axis: Axis) {
		Axis.forEach { get(it).apply(target, space, region, axis) }
	}
}
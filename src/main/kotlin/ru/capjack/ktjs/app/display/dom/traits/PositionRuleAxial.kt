package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxial

class PositionRuleAxial(x: PositionRule, y: PositionRule) : PositionRule, DimensionRuleAxial<PositionRule>(x, y) {
	override fun apply(target: MutableAxial<Int>, space: Axial<Int>, region: Axial<Int>) {
		Axis.forEach { get(it).apply(target, space, region, it) }
	}
	
	override fun apply(target: MutableAxial<Int>, space: Axial<Int>, region: Axial<Int>, axis: Axis) {
		Axis.forEach { get(it).apply(target, space, region, axis) }
	}
}
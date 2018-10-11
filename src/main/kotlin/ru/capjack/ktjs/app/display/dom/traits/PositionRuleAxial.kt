package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxial

open class PositionRuleAxial(x: PositionRule, y: PositionRule) : PositionRule, DimensionRuleAxial<PositionRule>(x, y) {
	override fun apply(target: MutableAxial<Int>, position: Axial<Int>, space: Axial<Int>, region: Axial<Int>) {
		Axis.forEach { apply(target, position, space, region, it) }
	}
	
	override fun apply(target: MutableAxial<Int>, position: Axial<Int>, space: Axial<Int>, region: Axial<Int>, axis: Axis) {
		get(axis).apply(target, position, space, region, axis)
	}
}
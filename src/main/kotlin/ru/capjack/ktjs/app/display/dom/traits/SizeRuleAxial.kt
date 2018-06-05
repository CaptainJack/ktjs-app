package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxial

class SizeRuleAxial(x: SizeRule, y: SizeRule) : SizeRule, DimensionRuleAxial<SizeRule>(x, y) {
	override fun apply(target: MutableAxial<Int>, space: Axial<Int>, type: SpaceType) {
		Axis.forEach { get(it).apply(target, space, type, it) }
	}
	
	override fun apply(target: MutableAxial<Int>, space: Axial<Int>, type: SpaceType, axis: Axis) {
		Axis.forEach { get(it).apply(target, space, type, axis) }
	}
}
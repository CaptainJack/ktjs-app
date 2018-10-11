package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxial

open class SizeRuleAxial(x: SizeRule, y: SizeRule) : SizeRule, DimensionRuleAxial<SizeRule>(x, y) {
	override fun apply(target: MutableAxial<Int>, space: Axial<Int>, type: SpaceType) {
		Axis.forEach { apply(target, space, type, it) }
	}
	
	override fun apply(target: MutableAxial<Int>, space: Axial<Int>, type: SpaceType, axis: Axis) {
		get(axis).apply(target, space, type, axis)
	}
}
package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxial

open class SizeRuleNothing : SizeRule, DimensionRuleNothing() {
	override fun apply(target: MutableAxial<Int>, space: Axial<Int>, type: SpaceType) {}
	
	override fun apply(target: MutableAxial<Int>, space: Axial<Int>, type: SpaceType, axis: Axis) {}
}
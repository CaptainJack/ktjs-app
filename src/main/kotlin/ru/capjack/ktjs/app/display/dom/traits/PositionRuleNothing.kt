package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxial

open class PositionRuleNothing : PositionRule, DimensionRuleNothing() {
	override fun apply(target: MutableAxial<Int>, position: Axial<Int>, space: Axial<Int>, region: Axial<Int>) {}
	
	override fun apply(target: MutableAxial<Int>, position: Axial<Int>, space: Axial<Int>, region: Axial<Int>, axis: Axis) {}
}
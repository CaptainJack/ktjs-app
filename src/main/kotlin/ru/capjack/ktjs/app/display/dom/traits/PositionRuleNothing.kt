package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxialValues

class PositionRuleNothing : PositionRule, DimensionRuleNothing() {
	override fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, region: AxialValues<Int>) {}
	
	override fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, region: AxialValues<Int>, axis: Axis) {}
}
package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxialValues

class SizeRuleNothing : SizeRule, DimensionRuleNothing() {
	override fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, type: SpaceType) {}
	
	override fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, type: SpaceType, axis: Axis) {}
}
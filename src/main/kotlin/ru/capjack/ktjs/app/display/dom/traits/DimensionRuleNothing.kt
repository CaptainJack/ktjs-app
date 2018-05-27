package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axis

abstract class DimensionRuleNothing : DimensionRule {
	override fun isApplicable(type: SpaceType) = false
	
	override fun isApplicable(type: SpaceType, axis: Axis) = false
}
package ru.capjack.ktjs.app.display.dom.traits

abstract class DimensionRuleNothing : DimensionRule {
	override fun isApplicable(type: SpaceType) = false
}
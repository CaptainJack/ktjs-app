package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.AxialValuesImpl

abstract class DimensionRuleAxial<T : DimensionRule>(x: T, y: T) : DimensionRule, AxialValuesImpl<T>(x, y) {
	override fun isApplicable(type: SpaceType): Boolean {
		return x.isApplicable(type) || y.isApplicable(type)
	}
}
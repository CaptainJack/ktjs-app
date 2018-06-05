package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.AxialImpl
import ru.capjack.ktjs.common.geom.Axis

abstract class DimensionRuleAxial<T : DimensionRule>(x: T, y: T) : DimensionRule, AxialImpl<T>(x, y) {
	override fun isApplicable(type: SpaceType): Boolean {
		return x.isApplicable(type) || y.isApplicable(type)
	}
	
	override fun isApplicable(type: SpaceType, axis: Axis): Boolean {
		return get(axis).isApplicable(type)
	}
}
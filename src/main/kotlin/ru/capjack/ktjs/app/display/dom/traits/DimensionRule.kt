package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axis

interface DimensionRule {
	fun isApplicable(type: SpaceType): Boolean
	
	fun isApplicable(type: SpaceType, axis: Axis): Boolean
}

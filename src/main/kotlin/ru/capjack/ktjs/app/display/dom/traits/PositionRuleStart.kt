package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis

class PositionRuleStart : PositionRuleCalculable() {
	
	override fun isApplicable(type: SpaceType): Boolean = false
	
	override fun isApplicable(type: SpaceType, axis: Axis): Boolean = false
	
	override fun calculate(position: Axial<Int>, space: Axial<Int>, region: Axial<Int>, axis: Axis): Int {
		return position[axis]
	}
}
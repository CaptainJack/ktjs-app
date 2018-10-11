package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis

open class PositionRuleCenter : PositionRuleCalculable() {
	override fun isApplicable(type: SpaceType): Boolean = true
	
	override fun isApplicable(type: SpaceType, axis: Axis): Boolean = true
	
	override fun calculate(position: Axial<Int>, space: Axial<Int>, region: Axial<Int>, axis: Axis): Int {
		return (space[axis] - region[axis]) / 2 + position[axis]
	}
}
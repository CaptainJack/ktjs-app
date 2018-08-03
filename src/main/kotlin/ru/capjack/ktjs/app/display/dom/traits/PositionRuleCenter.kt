package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis

class PositionRuleCenter(offset: Int) : PositionRuleCalculableValuable(offset) {
	override fun calculate(space: Axial<Int>, region: Axial<Int>, axis: Axis) = (space[axis] - region[axis]) / 2 + value
}
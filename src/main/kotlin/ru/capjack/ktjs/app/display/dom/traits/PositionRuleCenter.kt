package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis

class PositionRuleCenter : PositionRuleCalculable() {
	override fun calculate(space: AxialValues<Int>, region: AxialValues<Int>, axis: Axis) = (space[axis] - region[axis]) / 2
}
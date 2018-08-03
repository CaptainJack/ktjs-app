package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis

class PositionRuleStart(offset: Int) : PositionRuleCalculableValuable(offset) {
	override fun calculate(space: Axial<Int>, region: Axial<Int>, axis: Axis) = value
}
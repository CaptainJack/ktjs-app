package ru.capjack.ktjs.app.display.nodes.node

import ru.capjack.ktjs.app.display.nodes.Node
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis

class PositionRulesNothing : PositionRule {
	override fun apply(node: Node, space: AxialValues<Int>) {}
	override fun apply(node: Node, space: AxialValues<Int>, axis: Axis) {}
}
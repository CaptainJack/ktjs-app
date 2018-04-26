package ru.capjack.ktjs.app.display.nodes.node

import ru.capjack.ktjs.app.display.nodes.Node
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis

class PositionRulesNode : PositionRule {
	override fun apply(node: Node, space: AxialValues<Int>) {
		node.positionRule.apply(node, space)
	}
	
	override fun apply(node: Node, space: AxialValues<Int>, axis: Axis) {
		node.positionRule.apply(node, space, axis)
	}
}
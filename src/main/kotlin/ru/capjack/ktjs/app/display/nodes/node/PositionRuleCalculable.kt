package ru.capjack.ktjs.app.display.nodes.node

import ru.capjack.ktjs.app.display.nodes.Node
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis

abstract class PositionRuleCalculable : PositionRule {
	override fun apply(node: Node, space: AxialValues<Int>) {
		node.position.set(calculate(node, space, Axis.HORIZONTAL), calculate(node, space, Axis.VERTICAL))
	}
	
	override fun apply(node: Node, space: AxialValues<Int>, axis: Axis) {
		node.position[axis] = calculate(node, space, axis)
	}
	
	abstract fun calculate(node: Node, space: AxialValues<Int>, axis: Axis): Int
}
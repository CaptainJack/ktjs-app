package ru.capjack.ktjs.app.display.nodes.node

import ru.capjack.ktjs.app.display.nodes.Node
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis

open class PositionRuleConstant(private val value: Int) : PositionRule {
	override fun apply(node: Node, space: AxialValues<Int>) {
		node.position.set(value, value)
	}
	
	override fun apply(node: Node, space: AxialValues<Int>, axis: Axis) {
		node.position[axis] = value
	}
}
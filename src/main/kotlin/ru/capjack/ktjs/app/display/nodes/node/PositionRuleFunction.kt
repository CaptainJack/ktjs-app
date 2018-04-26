package ru.capjack.ktjs.app.display.nodes.node

import ru.capjack.ktjs.app.display.nodes.Node
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis

open class PositionRuleFunction(private val fn: (node: Node, space: AxialValues<Int>, axis: Axis) -> Int) : PositionRuleCalculable() {
	override fun calculate(node: Node, space: AxialValues<Int>, axis: Axis): Int = fn(node, space, axis)
}
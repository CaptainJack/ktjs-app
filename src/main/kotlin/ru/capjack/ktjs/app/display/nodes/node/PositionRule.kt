package ru.capjack.ktjs.app.display.nodes.node

import ru.capjack.ktjs.app.display.nodes.Node
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis

interface PositionRule {
	fun apply(node: Node, space: AxialValues<Int>)
	
	fun apply(node: Node, space: AxialValues<Int>, axis: Axis)
}
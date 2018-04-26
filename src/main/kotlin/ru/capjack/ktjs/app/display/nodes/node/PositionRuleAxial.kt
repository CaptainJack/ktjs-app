package ru.capjack.ktjs.app.display.nodes.node

import ru.capjack.ktjs.app.display.nodes.Node
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.AxialValuesImpl
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.forEach

open class PositionRuleAxial(horizontal: PositionRule, vertical: PositionRule) : AxialValuesImpl<PositionRule>(horizontal, vertical),
	PositionRule {
	override fun apply(node: Node, space: AxialValues<Int>) {
		forEach { axis, value -> value.apply(node, space, axis) }
	}
	
	override fun apply(node: Node, space: AxialValues<Int>, axis: Axis) {
		get(axis).apply(node, space, axis)
	}
}
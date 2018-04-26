package ru.capjack.ktjs.app.display.nodes.box

import ru.capjack.ktjs.app.display.nodes.Node
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxialValuesImpl
import ru.capjack.ktjs.app.display.nodes.node.PositionRule
import ru.capjack.ktjs.app.display.nodes.node.PositionRules
import ru.capjack.ktjs.app.display.nodes.node.SizeExpansion
import kotlin.math.max

class StackLayout(
	private val axis: Axis,
	private val gap: Int = 0,
	dynamic: Boolean = false,
	private val oppositePositionRule: PositionRule = PositionRules.NODE

) : Layout {
	override val dependentOnChildrenChange: Boolean = true
	override val dependentOnChildSizeChange: Boolean = dynamic
	
	override fun apply(nodes: List<Node>, space: AxialValues<Int>) {
		val oppositeAxis = axis.opposite
		
		var fillingWeight = 0
		val fillingSpace = MutableAxialValuesImpl(space)
		
		if (gap > 0) {
			fillingSpace[axis] -= gap * (nodes.size - 1)
		}
		
		for (node in nodes) {
			if (node.sizeRule.isExpansion(SizeExpansion.FILLING, axis)) {
				++fillingWeight
			}
			else {
				fillingSpace[axis] -= node.size[axis]
			}
			if (!node.sizeRule.isExpansion(SizeExpansion.FILLING, oppositeAxis)) {
				fillingSpace[oppositeAxis] = max(fillingSpace[oppositeAxis], node.size[oppositeAxis])
			}
		}
		
		if (fillingSpace[axis] < 0) {
			fillingSpace[axis] = 0
		}
		
		if (fillingWeight > 0) {
			fillingSpace[axis] /= fillingWeight
		}
		
		var position = 0
		
		for (node in nodes) {
			node.sizeRule.apply(node.size, fillingSpace, SizeExpansion.FILLING)
			node.position[axis] = position
			oppositePositionRule.apply(node, fillingSpace, oppositeAxis)
			position += node.size[axis] + gap
		}
	}
	
	override fun apply(node: Node, space: AxialValues<Int>) {
	}
}

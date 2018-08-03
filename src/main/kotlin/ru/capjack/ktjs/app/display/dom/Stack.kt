package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.app.display.dom.traits.PositionRule
import ru.capjack.ktjs.app.display.dom.traits.PositionRules
import ru.capjack.ktjs.app.display.dom.traits.SpaceType
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.mutableAxial

class Stack(
	private val axis: Axis,
	private val gap: Int = 0,
	private val align: PositionRule = PositionRules.NOTHING
) : Container() {
	
	override fun processChangeNodes() {
		var offset = 0
		
		val oppositeAxis = axis.opposite
		val space = mutableAxial(0, 0)
		val generalAlign = align.isApplicable(SpaceType.OUTSIDE, oppositeAxis)
		
		if (sizeRule.isApplicable(SpaceType.INSIDE, oppositeAxis)) {
			space[oppositeAxis] = nodes.map { it.size[oppositeAxis] }.max() ?: 0
		}
		else {
			space[oppositeAxis] = size[oppositeAxis]
		}
		
		
		for (node in nodes) {
			node.position[axis] = offset
			offset += gap + node.size[axis]
			
			if (generalAlign) {
				align.apply(node.position, space, node.size, oppositeAxis)
			}
			else {
				node.positionRule.apply(node.position, space, node.size, oppositeAxis)
			}
		}
		
		super.processChangeNodes()
	}
}
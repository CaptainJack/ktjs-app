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
	
	private var skipPlace: Boolean = false
	
	init {
		size.onChange(::placeNodes)
	}
	
	override fun isAllowsChildrenSizeOutside(axis: Axis): Boolean {
		return axis != this.axis
	}
	
	override fun processChangeNodes() {
		placeNodes()
		super.processChangeNodes()
	}
	
	override fun updateContentSize() {
		skipPlace = true
		super.updateContentSize()
		skipPlace = false
	}
	
	private fun placeNodes() {
		if (skipPlace) {
			return
		}
		
		var offset = 0
		
		val oppositeAxis = axis.opposite
		val space = mutableAxial(0, 0)
		val generalAlign = align.isApplicable(SpaceType.OUTSIDE, oppositeAxis)
		
		if (sizeRule.isApplicable(SpaceType.INSIDE, oppositeAxis)) {
			space[oppositeAxis] = nodes.map { it.size[oppositeAxis] }.max() ?: 0
		}
		else {
			space[oppositeAxis] = innerSize[oppositeAxis]
		}
		
		space[axis] = innerSize[axis]
		
		val nodesForOutsideSize = mutableListOf<Node>()
		
		for (node in nodes) {
			if (node.sizeRule.isApplicable(SpaceType.OUTSIDE, axis)) {
				nodesForOutsideSize.add(node)
			}
			else {
				space[axis] -= node.size[axis]
			}
		}
		
		space[axis] = (space[axis] - (nodes.size - 1).coerceAtLeast(0) * gap).coerceAtLeast(0)
		
		if (nodesForOutsideSize.isNotEmpty()) {
			val oneOutsideSize = space[axis] / nodesForOutsideSize.size
			for (node in nodesForOutsideSize) {
				node.size[axis] = oneOutsideSize
			}
		}
		
		for (node in nodes) {
			node.position[axis] = offset
			offset += gap + node.size[axis]
			
			if (generalAlign) {
				node.applyPositionRule(align, space, oppositeAxis)
			}
			else {
				node.applyPositionRule(node.positionRule, space, oppositeAxis)
			}
		}
	}
}
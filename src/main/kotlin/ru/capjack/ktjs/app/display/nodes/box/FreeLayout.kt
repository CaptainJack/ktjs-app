package ru.capjack.ktjs.app.display.nodes.box

import ru.capjack.ktjs.app.display.nodes.Node
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.app.display.nodes.node.SizeExpansion

class FreeLayout(dynamic: Boolean) : Layout {
	override val dependentOnChildrenChange: Boolean = false
	override val dependentOnChildSizeChange: Boolean = dynamic
	
	override fun apply(nodes: List<Node>, space: AxialValues<Int>) {
		nodes.forEach { apply(it, space) }
	}
	
	override fun apply(node: Node, space: AxialValues<Int>) {
		node.sizeRule.apply(node.size, space, SizeExpansion.FILLING)
		node.positionRule.apply(node, space)
	}
}
package ru.capjack.ktjs.app.display.nodes.box

import ru.capjack.ktjs.app.display.nodes.Node
import ru.capjack.ktjs.common.geom.AxialValues

interface Layout {
	val dependentOnChildrenChange: Boolean
	val dependentOnChildSizeChange: Boolean
	
	fun apply(nodes: List<Node>, space: AxialValues<Int>)
	
	fun apply(node: Node, space: AxialValues<Int>)
}


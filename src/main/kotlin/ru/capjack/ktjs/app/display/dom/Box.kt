package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.app.display.dom.traits.SizeRules
import ru.capjack.ktjs.common.geom.mutableAxial
import ru.capjack.ktjs.common.geom.setMax

open class Box : Container() {
	init {
		sizeRule = SizeRules.STRETCHING
	}
	
	override fun processNodesChanged() {
		super.processNodesChanged()
		
		val cs = mutableAxial(0)
		
		for (node in nodes) {
			cs.setMax(node.size.x, node.size.y)
		}
		
		_contentSize.set(cs)
	}
}
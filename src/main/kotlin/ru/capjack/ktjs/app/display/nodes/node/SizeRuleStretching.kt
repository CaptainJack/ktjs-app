package ru.capjack.ktjs.app.display.nodes.node

import ru.capjack.ktjs.common.geom.Axis

class SizeRuleStretching : SizeRule {
	override fun isExpansion(expansion: SizeExpansion): Boolean {
		return expansion == SizeExpansion.STRETCHING
	}
	
	override fun isExpansion(expansion: SizeExpansion, axis: Axis): Boolean {
		return expansion == SizeExpansion.STRETCHING
	}
}
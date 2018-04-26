package ru.capjack.ktjs.app.display.nodes.node

import ru.capjack.ktjs.common.geom.Axis

class SizeRuleFilling : SizeRule {
	override fun isExpansion(expansion: SizeExpansion): Boolean {
		return expansion == SizeExpansion.FILLING
	}
	
	override fun isExpansion(expansion: SizeExpansion, axis: Axis): Boolean {
		return expansion == SizeExpansion.FILLING
	}
}
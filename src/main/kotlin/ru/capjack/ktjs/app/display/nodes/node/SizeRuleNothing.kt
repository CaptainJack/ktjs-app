package ru.capjack.ktjs.app.display.nodes.node

import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxialValues

class SizeRuleNothing : SizeRule {
	override fun isExpansion(expansion: SizeExpansion): Boolean = false
	
	override fun isExpansion(expansion: SizeExpansion, axis: Axis): Boolean = false
	
	override fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, expansion: SizeExpansion) {}
}
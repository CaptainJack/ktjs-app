package ru.capjack.ktjs.app.display.nodes.node

import ru.capjack.ktjs.common.geom.AxialValuesImpl
import ru.capjack.ktjs.common.geom.Axis

class SizeRuleAxial(horizontal: SizeRule, vertical: SizeRule) : AxialValuesImpl<SizeRule>(horizontal, vertical),
	SizeRule {
	override fun isExpansion(expansion: SizeExpansion): Boolean {
		return horizontal.isExpansion(expansion) && vertical.isExpansion(expansion)
	}
	
	override fun isExpansion(expansion: SizeExpansion, axis: Axis): Boolean {
		return get(axis).isExpansion(expansion)
	}
}
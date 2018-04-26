package ru.capjack.ktjs.app.display.nodes.box

import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.app.display.nodes.node.PositionRule
import ru.capjack.ktjs.app.display.nodes.node.PositionRules

object Layouts {
	val FREE: Layout = FreeLayout(false)
	val FREE_DYNAMIC: Layout = FreeLayout(true)
	
	fun horizontal(gap: Int, dynamic: Boolean = false, oppositePositionRule: PositionRule = PositionRules.NODE): Layout {
		return StackLayout(Axis.HORIZONTAL, gap, dynamic, oppositePositionRule)
	}
	
	fun vertical(gap: Int, dynamic: Boolean = false, oppositePositionRule: PositionRule = PositionRules.NODE): Layout {
		return StackLayout(Axis.VERTICAL, gap, dynamic, oppositePositionRule)
	}
}
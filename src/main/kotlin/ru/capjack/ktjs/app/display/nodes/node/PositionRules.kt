package ru.capjack.ktjs.app.display.nodes.node

import ru.capjack.ktjs.app.display.nodes.Node
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis

object PositionRules {
	val NOTHING: PositionRule = PositionRulesNothing()
	
	val NODE: PositionRule = PositionRulesNode()
	
	val START: PositionRule = PositionRuleConstant(0)
	val END: PositionRule = factory { node, space, axis ->
		space[axis] - node.size[axis]
	}
	val CENTER: PositionRule = factory { node, space, axis ->
		(space[axis] - node.size[axis]) / 2
	}
	
	val LEFT_TOP: PositionRule = START
	val LEFT_CENTER: PositionRule = factory(
		START,
		CENTER
	)
	val LEFT_BOTTOM: PositionRule = factory(
		START,
		END
	)
	
	val CENTER_TOP: PositionRule = factory(
		CENTER,
		START
	)
	val CENTER_CENTER: PositionRule = CENTER
	val CENTER_BOTTOM: PositionRule = factory(
		CENTER,
		END
	)
	
	val RIGHT_TOP: PositionRule = factory(
		END,
		START
	)
	val RIGHT_CENTER: PositionRule = factory(
		END,
		CENTER
	)
	val RIGHT_BOTTOM: PositionRule = END
	
	fun factory(horizontal: PositionRule, vertical: PositionRule): PositionRule {
		return PositionRuleAxial(horizontal, vertical)
	}
	
	fun factory(horizontal: PositionRule, vertical: Int): PositionRule {
		return factory(horizontal, PositionRuleConstant(vertical))
	}
	
	fun factory(horizontal: Int, vertical: PositionRule): PositionRule {
		return factory(PositionRuleConstant(horizontal), vertical)
	}
	
	fun factory(fn: (node: Node, space: AxialValues<Int>, axis: Axis) -> Int): PositionRule {
		return PositionRuleFunction(fn)
	}
}


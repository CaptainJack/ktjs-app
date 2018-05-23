package ru.capjack.ktjs.app.display.dom.traits

object PositionRules {
	val NOTHING: PositionRule = PositionRuleNothing()
	
	val CENTER: PositionRule = PositionRuleCenter()
	val START: PositionRule = NOTHING
	val END: PositionRule = PositionRuleEnd()
	
	val LEFT: PositionRule = START
	val RIGHT: PositionRule = END
	
	val TOP: PositionRule = START
	val BOTTOM: PositionRule = END
	
	val CENTER_TOP: PositionRule = PositionRuleAxial(CENTER, TOP)
	val CENTER_BOTTOM: PositionRule = PositionRuleAxial(CENTER, BOTTOM)
	
	val LEFT_CENTER: PositionRule = PositionRuleAxial(LEFT, CENTER)
	val LEFT_TOP: PositionRule = START
	val LEFT_BOTTOM: PositionRule = PositionRuleAxial(LEFT, BOTTOM)
	
	val RIGHT_CENTER: PositionRule = PositionRuleAxial(RIGHT, CENTER)
	val RIGHT_TOP: PositionRule = PositionRuleAxial(RIGHT, TOP)
	val RIGHT_BOTTOM: PositionRule = END
	
	fun factory(x: PositionRule, y: PositionRule): PositionRule {
		return when (x) {
			CENTER -> {
				when (y) {
					CENTER -> CENTER
					TOP    -> CENTER_TOP
					BOTTOM -> CENTER_BOTTOM
					else   -> PositionRuleAxial(x, y)
				}
			}
			LEFT   -> {
				when (y) {
					CENTER -> LEFT_CENTER
					TOP    -> LEFT_TOP
					BOTTOM -> LEFT_BOTTOM
					else   -> PositionRuleAxial(x, y)
				}
			}
			RIGHT  -> {
				when (y) {
					CENTER -> RIGHT_CENTER
					TOP    -> RIGHT_TOP
					BOTTOM -> RIGHT_BOTTOM
					else   -> PositionRuleAxial(x, y)
				}
			}
			else   -> PositionRuleAxial(x, y)
		}
	}
}
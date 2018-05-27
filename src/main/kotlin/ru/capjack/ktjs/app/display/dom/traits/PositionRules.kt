package ru.capjack.ktjs.app.display.dom.traits

object PositionRules {
	val NOTHING: PositionRule = PositionRuleNothing()
	
	val CENTER: PositionRule = PositionRuleCenter()
	val START: PositionRule = NOTHING
	val END: PositionRule = PositionRuleEnd()
	
	val CENTER_TOP: PositionRule = PositionRuleAxial(CENTER, START)
	val CENTER_BOTTOM: PositionRule = PositionRuleAxial(CENTER, END)
	
	val LEFT_CENTER: PositionRule = PositionRuleAxial(START, CENTER)
	val LEFT_TOP: PositionRule = START
	val LEFT_BOTTOM: PositionRule = PositionRuleAxial(START, END)
	
	val RIGHT_CENTER: PositionRule = PositionRuleAxial(END, CENTER)
	val RIGHT_TOP: PositionRule = PositionRuleAxial(END, START)
	val RIGHT_BOTTOM: PositionRule = END
	
	fun factory(x: PositionRule, y: PositionRule): PositionRule {
		return when (x) {
			CENTER -> {
				when (y) {
					CENTER -> CENTER
					START  -> CENTER_TOP
					END    -> CENTER_BOTTOM
					else   -> PositionRuleAxial(x, y)
				}
			}
			START  -> {
				when (y) {
					CENTER -> LEFT_CENTER
					START  -> LEFT_TOP
					END    -> LEFT_BOTTOM
					else   -> PositionRuleAxial(x, y)
				}
			}
			END    -> {
				when (y) {
					CENTER -> RIGHT_CENTER
					START  -> RIGHT_TOP
					END    -> RIGHT_BOTTOM
					else   -> PositionRuleAxial(x, y)
				}
			}
			else   -> PositionRuleAxial(x, y)
		}
	}
}
package ru.capjack.ktjs.app.display.dom.traits

object PositionRules {
	val NOTHING: PositionRule = PositionRuleNothing()
	
	val CENTER: PositionRule = PositionRuleCenter(0)
	val START: PositionRule = PositionRuleStart(0)
	val END: PositionRule = PositionRuleEnd(0)
	
	val NOTHING_CENTER: PositionRule = PositionRuleAxial(NOTHING, CENTER)
	val NOTHING_TOP: PositionRule = PositionRuleAxial(NOTHING, START)
	val NOTHING_BOTTOM: PositionRule = PositionRuleAxial(NOTHING, END)
	
	val CENTER_NOTHING: PositionRule = PositionRuleAxial(CENTER, NOTHING)
	val CENTER_TOP: PositionRule = PositionRuleAxial(CENTER, START)
	val CENTER_BOTTOM: PositionRule = PositionRuleAxial(CENTER, END)
	
	val LEFT_NOTHING: PositionRule = PositionRuleAxial(START, NOTHING)
	val LEFT_CENTER: PositionRule = PositionRuleAxial(START, CENTER)
	val LEFT_TOP: PositionRule = START
	val LEFT_BOTTOM: PositionRule = PositionRuleAxial(START, END)
	
	val RIGHT_NOTHING: PositionRule = PositionRuleAxial(END, NOTHING)
	val RIGHT_CENTER: PositionRule = PositionRuleAxial(END, CENTER)
	val RIGHT_TOP: PositionRule = PositionRuleAxial(END, START)
	val RIGHT_BOTTOM: PositionRule = END
	
	fun start(offset: Int): PositionRule {
		return if (offset == 0) START else PositionRuleStart(offset)
	}
	
	fun end(offset: Int): PositionRule {
		return if (offset == 0) END else PositionRuleEnd(offset)
	}
	
	fun center(offset: Int): PositionRule {
		return if (offset == 0) CENTER else PositionRuleCenter(offset)
	}
	
	fun factory(x: PositionRule, y: PositionRule): PositionRule {
		return when (x) {
			NOTHING -> {
				when (y) {
					NOTHING -> NOTHING
					CENTER  -> NOTHING_CENTER
					START   -> NOTHING_TOP
					END     -> NOTHING_BOTTOM
					else    -> PositionRuleAxial(x, y)
				}
			}
			CENTER -> {
				when (y) {
					NOTHING -> CENTER_NOTHING
					CENTER  -> CENTER
					START   -> CENTER_TOP
					END     -> CENTER_BOTTOM
					else    -> PositionRuleAxial(x, y)
				}
			}
			START  -> {
				when (y) {
					NOTHING -> LEFT_NOTHING
					CENTER  -> LEFT_CENTER
					START   -> LEFT_TOP
					END     -> LEFT_BOTTOM
					else    -> PositionRuleAxial(x, y)
				}
			}
			END    -> {
				when (y) {
					NOTHING -> RIGHT_NOTHING
					CENTER  -> RIGHT_CENTER
					START   -> RIGHT_TOP
					END     -> RIGHT_BOTTOM
					else    -> PositionRuleAxial(x, y)
				}
			}
			else   -> PositionRuleAxial(x, y)
		}
	}
}
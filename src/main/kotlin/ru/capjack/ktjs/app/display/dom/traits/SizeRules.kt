package ru.capjack.ktjs.app.display.dom.traits

object SizeRules {
	val NOTHING: SizeRule = SizeRuleNothing()
	val STRETCHING: SizeRule = SizeRuleTypedExact(SpaceType.INSIDE)
	val FILLING: SizeRule = SizeRuleTypedExact(SpaceType.OUTSIDE)
	
	val NOTHING_STRETCHING: SizeRule = SizeRuleAxial(NOTHING, STRETCHING)
	val NOTHING_FILLING: SizeRule = SizeRuleAxial(NOTHING, FILLING)
	
	val STRETCHING_NOTHING: SizeRule = SizeRuleAxial(STRETCHING, NOTHING)
	val STRETCHING_FILLING: SizeRule = SizeRuleAxial(STRETCHING, FILLING)
	
	val FILLING_NOTHING: SizeRule = SizeRuleAxial(FILLING, NOTHING)
	val FILLING_STRETCHING: SizeRule = SizeRuleAxial(FILLING, STRETCHING)
	
	fun factory(x: SizeRule, y: SizeRule): SizeRule {
		return when (x) {
			NOTHING    -> when (y) {
				NOTHING    -> NOTHING
				STRETCHING -> NOTHING_STRETCHING
				FILLING    -> NOTHING_FILLING
				else       -> SizeRuleAxial(x, y)
			}
			STRETCHING -> when (y) {
				NOTHING    -> STRETCHING_NOTHING
				STRETCHING -> STRETCHING
				FILLING    -> STRETCHING_FILLING
				else       -> SizeRuleAxial(x, y)
			}
			FILLING    -> when (y) {
				NOTHING    -> FILLING_NOTHING
				STRETCHING -> FILLING_STRETCHING
				FILLING    -> FILLING
				else       -> SizeRuleAxial(x, y)
			}
			else       -> SizeRuleAxial(x, y)
		}
	}
}
package ru.capjack.ktjs.app.display.nodes.node

object SizeRules {
	val NOTHING: SizeRule = SizeRuleNothing()
	val STRETCHING: SizeRule = SizeRuleStretching()
	val FILLING: SizeRule = SizeRuleFilling()
	
	fun factory(horizontal: SizeRule, vertical: SizeRule): SizeRule {
		return SizeRuleAxial(horizontal, vertical)
	}
}


package ru.capjack.ktjs.app.display.dom.traits

class SizeRuleTypedExact(private val type: SpaceType) : SizeRuleExact() {
	override fun isApplicable(type: SpaceType): Boolean  = this.type == type
}
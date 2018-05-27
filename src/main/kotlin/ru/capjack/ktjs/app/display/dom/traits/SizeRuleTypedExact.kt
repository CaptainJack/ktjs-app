package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axis

class SizeRuleTypedExact(private val type: SpaceType) : SizeRuleExact() {
	override fun isApplicable(type: SpaceType) = this.type == type
	
	override fun isApplicable(type: SpaceType, axis: Axis) = this.type == type
}
package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxial

abstract class SizeRuleExact : SizeRule {
	override fun apply(target: MutableAxial<Int>, space: Axial<Int>, type: SpaceType) {
		if (isApplicable(type)) {
			target.set(space)
		}
	}
	
	override fun apply(target: MutableAxial<Int>, space: Axial<Int>, type: SpaceType, axis: Axis) {
		if (isApplicable(type, axis)) {
			target[axis] = space[axis]
		}
	}
}
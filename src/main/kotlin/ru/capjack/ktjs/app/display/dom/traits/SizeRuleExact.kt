package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxialValues

abstract class SizeRuleExact : SizeRule {
	override fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, type: SpaceType) {
		if (isApplicable(type)) {
			target.set(space)
		}
	}
	
	override fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, type: SpaceType, axis: Axis) {
		if (isApplicable(type, axis)) {
			target[axis] = space[axis]
		}
	}
}
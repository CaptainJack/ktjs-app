package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxialValues

interface SizeRule : DimensionRule {
	fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, type: SpaceType)
	
	fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, type: SpaceType, axis: Axis)
}



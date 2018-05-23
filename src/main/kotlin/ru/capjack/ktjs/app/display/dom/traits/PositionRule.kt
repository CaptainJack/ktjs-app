package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxialValues

interface PositionRule : DimensionRule {
	fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, region: AxialValues<Int>)
	
	fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, region: AxialValues<Int>, axis: Axis)
}
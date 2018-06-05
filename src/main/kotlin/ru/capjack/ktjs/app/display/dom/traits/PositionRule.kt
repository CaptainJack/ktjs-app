package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxial

interface PositionRule : DimensionRule {
	fun apply(target: MutableAxial<Int>, space: Axial<Int>, region: Axial<Int>)
	
	fun apply(target: MutableAxial<Int>, space: Axial<Int>, region: Axial<Int>, axis: Axis)
}
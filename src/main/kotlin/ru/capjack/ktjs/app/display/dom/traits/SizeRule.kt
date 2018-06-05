package ru.capjack.ktjs.app.display.dom.traits

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxial

interface SizeRule : DimensionRule {
	fun apply(target: MutableAxial<Int>, space: Axial<Int>, type: SpaceType)
	
	fun apply(target: MutableAxial<Int>, space: Axial<Int>, type: SpaceType, axis: Axis)
}



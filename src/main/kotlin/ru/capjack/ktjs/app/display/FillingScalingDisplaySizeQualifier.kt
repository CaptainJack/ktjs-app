package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.dom.traits.SizeRules
import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.AxialInstances
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.map
import ru.capjack.ktjs.common.geom.mapValues
import ru.capjack.ktjs.common.geom.min
import ru.capjack.ktjs.common.geom.mutableAxial
import ru.capjack.ktjs.common.geom.toMutable

class FillingScalingDisplaySizeQualifier(
	private val minSize: Axial<Int>,
	private val stretchAxis: Axis,
	private val limit: Int
) : DisplaySizeQualifier {
	override fun qualify(size: Axial<Int>): DisplaySize {
		val targetSize = size.map { a, v -> v.coerceAtLeast(minSize[a]) }
		
		val stageScale = targetSize.map { a, v -> v / minSize[a].toDouble() }.min
		
		val stageSize = targetSize.mapValues { (it / stageScale).toInt() }.toMutable()
		val stagePosition = mutableAxial(0)
		
		if (stageSize[stretchAxis] > limit) {
			stageSize[stretchAxis] = limit
			stagePosition[stretchAxis] = ((targetSize[stretchAxis] - (limit * stageScale)) / 2).toInt()
		}
		SizeRules.STRETCHING
		
		return DisplaySize(
			AxialInstances.INT_0,
			size,
			stagePosition,
			stageSize,
			stageScale
		)
	}
}
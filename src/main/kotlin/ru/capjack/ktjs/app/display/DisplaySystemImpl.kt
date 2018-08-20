package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.resolution.ResolutionResolver
import ru.capjack.ktjs.common.Confines
import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.AxialImpl
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.calculateRatio
import ru.capjack.ktjs.common.geom.isInside
import ru.capjack.ktjs.common.geom.isInsideAtLeastOne
import ru.capjack.ktjs.common.geom.isOutside
import ru.capjack.ktjs.common.geom.mutableAxial
import ru.capjack.ktjs.common.time.TimeSystem

class DisplaySystemImpl(
	time: TimeSystem,
	resolutionResolver: ResolutionResolver,
	stageSizeConfines: Confines<Axial<Int>>,
	backgroundColor: Int = 0x888888
) : DisplaySystem {
	
	private val _stage = StageImpl(this, stageSizeConfines)
	
	override val stage: Stage get() = _stage
	override val renderer: DisplayRenderer = DisplayRendererImpl(stageSizeConfines.min, resolutionResolver, backgroundColor)
	
	init {
		time.onEachFrame { render() }
	}
	
	override fun setCanvasSize(width: Int, height: Int) {
		setCanvasSize(AxialImpl(width, height))
	}
	
	override fun setCanvasSize(size: Axial<Int>) {
		val innerSize = _stage.sizeConfines.min
		val outerSize = _stage.sizeConfines.max
		
		val sizeRatio = size.calculateRatio(Axis.X)
		val innerRatio = innerSize.calculateRatio(Axis.X)
		val outerRatio = outerSize.calculateRatio(Axis.X)
		
		val stageSize = mutableAxial(0)
		val stagePosition = mutableAxial(0)
		
		var stageScale = 1.0
		
		if (size.x <= outerSize.x && size.y <= outerSize.y && size.x >= innerSize.x && size.y >= innerSize.y) {
			stageSize.set(size)
		} else {
			
			if (size.isOutside(innerSize) && size.isInsideAtLeastOne(outerSize)) {
				val axis = if (size.x <= outerSize.x) Axis.X else Axis.Y
				stageSize[axis] = size[axis]
				stageSize[axis.opposite] = outerSize[axis.opposite]
			} else {
				val inscribeAxis: Axis
				
				val inside = size.isInside(innerSize) || size.isInsideAtLeastOne(innerSize)
				if (inside) {
					inscribeAxis = if (sizeRatio > innerRatio) Axis.Y else Axis.X
					stageSize[inscribeAxis] = innerSize[inscribeAxis]
				} else {
					inscribeAxis = if (sizeRatio > outerRatio) Axis.Y else Axis.X
					stageSize.set(outerSize)
				}
				
				stageScale = size[inscribeAxis].toDouble() / stageSize[inscribeAxis]
				
				if (inside) {
					val opposite = inscribeAxis.opposite
					stageSize[opposite] = (size[opposite] / stageScale).toInt().coerceIn(innerSize[opposite], outerSize[opposite])
				}
			}
			
			stagePosition.set(
				(size.x - (stageSize.x * stageScale).toInt()) / 2,
				(size.y - (stageSize.y * stageScale).toInt()) / 2
			)
		}
		
		renderer.resize(size)
		_stage.locate(stagePosition, stageSize, stageScale)
	}
	
	private fun render() {
		renderer.render(_stage.display)
	}
}


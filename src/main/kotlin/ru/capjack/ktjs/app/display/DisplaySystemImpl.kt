package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.resolution.ResolutionResolver
import ru.capjack.ktjs.common.Confines
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.AxialValuesImpl
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
	stageSizeConfines: Confines<AxialValues<Int>>
) : DisplaySystem {
	
	private val _stage = StageImpl(stageSizeConfines)
	
	override val stage: Stage get() = _stage
	override val renderer: DisplayRenderer = DisplayRendererImpl(stageSizeConfines.min, resolutionResolver)
	
	init {
		time.onEachFrame { render() }
	}
	
	override fun setCanvasSize(width: Int, height: Int) {
		setCanvasSize(AxialValuesImpl(width, height))
	}
	
	override fun setCanvasSize(size: AxialValues<Int>) {
		val innerSize = _stage.sizeConfines.min
		val outerSize = _stage.sizeConfines.max
		
		val sizeRatio = size.calculateRatio(Axis.X)
		val innerRatio = innerSize.calculateRatio(Axis.X)
		val outerRatio = outerSize.calculateRatio(Axis.X)
		
		val rendererSize = mutableAxial(0)
		val stageSize = mutableAxial(0)
		val stagePosition = mutableAxial(0)
		
		var stageScale = 1.0
		
		if (size.isEquals(innerSize)) {
			rendererSize.set(innerSize)
			stageSize.set(innerSize)
		}
		else if (size.isEquals(outerSize)) {
			rendererSize.set(outerSize)
			stageSize.set(outerSize)
		}
		else if (size.isOutside(innerSize) && size.isInside(outerSize)) {
			rendererSize.set(size)
			stageSize.set(size)
		}
		else {
			if (size.isOutside(innerSize) && size.isInsideAtLeastOne(outerSize)) {
				val axis = if (size.x <= outerSize.x) Axis.X else Axis.Y
				rendererSize.set(size)
				stageSize[axis] = size[axis]
				stageSize[axis.opposite] = outerSize[axis.opposite]
			}
			else {
				
				val inscribeAxis: Axis
				val inscribeSize: AxialValues<Int>
				
				if (size.isInsideAtLeastOne(innerSize)) {
					inscribeAxis = if (sizeRatio > innerRatio) Axis.Y else Axis.X
					inscribeSize = innerSize
				}
				else {
					inscribeAxis = if (sizeRatio > outerRatio) Axis.Y else Axis.X
					inscribeSize = outerSize
				}
				
				val inscribeAxisOpposite = inscribeAxis.opposite
				rendererSize[inscribeAxis] = inscribeSize[inscribeAxis]
				rendererSize[inscribeAxisOpposite] = (inscribeSize[inscribeAxis] * size.calculateRatio(inscribeAxisOpposite)).toInt()
				
				stageSize[inscribeAxis] = inscribeSize[inscribeAxis]
				stageSize[inscribeAxisOpposite] = if (rendererSize[inscribeAxisOpposite] > outerSize[inscribeAxisOpposite]) {
					outerSize[inscribeAxisOpposite]
				}
				else {
					rendererSize[inscribeAxisOpposite]
				}
				
				stageScale = size[inscribeAxis] / rendererSize[inscribeAxis].toDouble()
			}
			
			stagePosition.set(
				(rendererSize.x - stageSize.x) / 2,
				(rendererSize.y - stageSize.y) / 2
			)
		}
		
		renderer.resize(rendererSize, size)
		_stage.locate(stagePosition, stageSize, stageScale)
	}
	
	private fun render() {
		renderer.render(_stage.display)
	}
}


package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.resolution.ResolutionResolver
import ru.capjack.ktjs.common.Confines
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.AxialValuesImpl
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxialValuesImpl
import ru.capjack.ktjs.common.geom.calculateRatio
import ru.capjack.ktjs.common.geom.isInside
import ru.capjack.ktjs.common.geom.isInsideAtLeastOne
import ru.capjack.ktjs.common.geom.isOutside
import ru.capjack.ktjs.common.time.TimeSystem

class DisplaySystemImpl(
	time: TimeSystem,
	resolutionResolver: ResolutionResolver,
	sizeConfines: Confines<AxialValues<Int>>

) : DisplaySystem {
	
	override val stage: Stage = Stage()
	
	override val traits: DisplaySystemTraits
		get() = _traits
	
	override val traitsPanel: DisplaySystemTraitsPanel by lazy {
		_traitsPanel = DisplaySystemTraitsPanelImpl(traits)
		_traitsPanel
	}
	
	private val _traits = DisplaySystemTraitsImpl(resolutionResolver, sizeConfines, stage.size)
	
	private var _traitsPanel: DisplaySystemTraitsPanelInternal = DisplaySystemTraitsPanelDummy()
	
	override val renderer: DisplayRenderer = DisplayRendererImpl(sizeConfines.min, traits.rendererResolution, stage.canvas)
	
	init {
		time.onEachFrame { render() }
	}
	
	override fun setCanvasSize(width: Int, height: Int) {
		setCanvasSize(AxialValuesImpl(width, height))
	}
	
	override fun setCanvasSize(size: AxialValues<Int>) {
		val innerSize = traits.stageSizeConfines.min
		val outerSize = traits.stageSizeConfines.max
		
		val sizeRatio = size.calculateRatio(Axis.HORIZONTAL)
		val innerRatio = innerSize.calculateRatio(Axis.HORIZONTAL)
		val outerRatio = outerSize.calculateRatio(Axis.HORIZONTAL)
		
		val rendererSize = MutableAxialValuesImpl(0)
		val stageSize = MutableAxialValuesImpl(0)
		val stagePosition = MutableAxialValuesImpl(0)
		
		_traits.stageScale = 1.0
		
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
				val axis = if (size.horizontal <= outerSize.horizontal) Axis.HORIZONTAL else Axis.VERTICAL
				rendererSize.set(size)
				stageSize[axis] = size[axis]
				stageSize[axis.opposite] = outerSize[axis.opposite]
			}
			else {
				
				val inscribeAxis: Axis
				val inscribeSize: AxialValues<Int>
				
				if (size.isInsideAtLeastOne(innerSize)) {
					inscribeAxis = if (sizeRatio > innerRatio) Axis.VERTICAL else Axis.HORIZONTAL
					inscribeSize = innerSize
				}
				else {
					inscribeAxis = if (sizeRatio > outerRatio) Axis.VERTICAL else Axis.HORIZONTAL
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
				
				_traits.stageScale = size[inscribeAxis] / rendererSize[inscribeAxis].toDouble()
			}
			
			stagePosition.horizontal = (rendererSize.horizontal - stageSize.horizontal) / 2
			stagePosition.vertical = (rendererSize.vertical - stageSize.vertical) / 2
		}
		
		renderer.resize(rendererSize.horizontal, rendererSize.vertical)
		stage.position.set(stagePosition)
		stage.size.set(stageSize)
		
		stage.canvas.style.width = "${size.horizontal}px"
		stage.canvas.style.height = "${size.vertical}px"
		
		_traitsPanel.updateSizes()
	}
	
	private fun render() {
		_traitsPanel.processBeginRender()
		
		renderer.render(stage.view)
		
		_traitsPanel.processEndRender()
	}
}


package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.resolution.ResolutionResolver
import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.AxialImpl
import ru.capjack.ktjs.common.time.TimeSystem

class DisplaySystemImpl(
	time: TimeSystem,
	resolutionResolver: ResolutionResolver,
	private val sizeQualifier: DisplaySizeQualifier,
	color: Int? = null
) : DisplaySystem {
	
	private val _stage = StageImpl(this)
	
	override val stage: Stage get() = _stage
	override val renderer: DisplayRenderer = DisplayRendererImpl(resolutionResolver, color)
	
	init {
		time.onEachFrame { render() }
	}
	
	override fun setSize(width: Int, height: Int) {
		setSize(AxialImpl(width, height))
	}
	
	override fun setSize(size: Axial<Int>) {
		sizeQualifier.qualify(size).also {
			renderer.locate(it.canvasPosition, it.canvasSize)
			_stage.locate(it.stagePosition, it.stageSize, it.stageScale)
		}
	}
	
	private fun render() {
		renderer.render(_stage.display)
	}
}


package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.common.geom.AxialValues

interface DisplaySystem {
	val stage: Stage
	
	val renderer: DisplayRenderer
	
	val traits: DisplaySystemTraits
	
	val traitsPanel: DisplaySystemTraitsPanel
	
	fun setCanvasSize(width: Int, height: Int)
	
	fun setCanvasSize(size: AxialValues<Int>)
}


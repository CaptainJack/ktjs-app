package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.common.geom.Axial

interface DisplaySystem {
	val stage: Stage
	
	val renderer: DisplayRenderer
	
	fun setCanvasSize(width: Int, height: Int)
	
	fun setCanvasSize(size: Axial<Int>)
}


package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.common.geom.Axial

interface DisplaySystem {
	val stage: Stage
	
	val renderer: DisplayRenderer
	
	val fullsrceen: DisplayFullsrceen
	
	fun setSize(width: Int, height: Int)
	
	fun setSize(size: Axial<Int>)
}


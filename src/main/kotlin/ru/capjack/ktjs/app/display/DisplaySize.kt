package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.common.geom.Axial

class DisplaySize(
	val canvasPosition: Axial<Int>,
	val canvasSize: Axial<Int>,
	val stagePosition: Axial<Int>,
	val stageSize: Axial<Int>,
	val stageScale: Double
)

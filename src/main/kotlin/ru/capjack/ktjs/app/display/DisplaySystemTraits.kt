package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.common.Confines
import ru.capjack.ktjs.common.geom.AxialValues

interface DisplaySystemTraits {
	val pixelRatio: Double
	val rendererResolution: Double
	val imageResolution: Int
	val stageSize: AxialValues<Int>
	val stageSizeConfines: Confines<AxialValues<Int>>
	val stageScale: Double
}
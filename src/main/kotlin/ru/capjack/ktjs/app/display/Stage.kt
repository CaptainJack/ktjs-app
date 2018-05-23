package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.dom.NodeList
import ru.capjack.ktjs.common.Confines
import ru.capjack.ktjs.common.geom.AxialValues

interface Stage : NodeList {
	val size: AxialValues<Int>
	val sizeConfines: Confines<AxialValues<Int>>
	val scale: Double
}


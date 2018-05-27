package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.dom.NodeList
import ru.capjack.ktjs.common.Confines
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.ChangeableAxialValues

interface Stage : NodeList {
	val size: ChangeableAxialValues<Int>
	val sizeConfines: Confines<AxialValues<Int>>
	val scale: Double
	
	fun showSizeTester()
}


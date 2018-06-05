package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.dom.NodeList
import ru.capjack.ktjs.common.Confines
import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.ChangeableAxial

interface Stage : NodeList {
	val size: ChangeableAxial<Int>
	val sizeConfines: Confines<Axial<Int>>
	val scale: Double
	
	fun showSizeTester()
}


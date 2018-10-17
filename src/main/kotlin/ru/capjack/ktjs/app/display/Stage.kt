package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.dom.NodeList
import ru.capjack.ktjs.common.geom.ChangeableAxial

interface Stage : NodeList {
	val displaySystem: DisplaySystem
	
	val size: ChangeableAxial<Int>
}


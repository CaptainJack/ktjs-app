package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.common.geom.Axial

interface DisplaySizeQualifier {
	fun qualify(size: Axial<Int>) : DisplaySize
}


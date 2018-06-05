package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.Rectangle
import ru.capjack.ktjs.common.geom.isEquals
import ru.capjack.ktjs.common.geom.rotate

data class ImageAtlasItem(
	val name: String,
	val frame: Rectangle<Int>,
	val size: Axial<Int>,
	val offset: Axial<Int>,
	val rotated: Boolean
) {
	val trimmed: Boolean
		get() = !(offset.isEquals(0) && (rotated && size.isEquals(frame.size.rotate()) || size.isEquals(frame.size)))
	
}
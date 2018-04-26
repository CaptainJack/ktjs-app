package ru.capjack.ktjs.app.display.pixi

import ru.capjack.ktjs.app.display.button.ButtonState
import ru.capjack.ktjs.app.display.button.ButtonStateValues
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Insets
import ru.capjack.ktjs.wrapper.pixi.Texture
import ru.capjack.ktjs.wrapper.pixi.mesh.NineSlicePlane
import ru.capjack.ktjs.wrapper.pixi.setSize

class ScaledTexturesDetail(
	textures: ButtonStateValues<Texture>,
	insets: Insets<Int>,
	size: AxialValues<Int>
) : BaseTexturesDetail(textures) {
	
	override val display = NineSlicePlane(textures[ButtonState.DISABLED], insets.left, insets.top, insets.right, insets.bottom)
	
	init {
		display.setSize(size)
	}
	
	override fun setStateValue(value: Texture) {
		display.texture = value
	}
}
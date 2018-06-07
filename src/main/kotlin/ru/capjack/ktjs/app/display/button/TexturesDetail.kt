package ru.capjack.ktjs.app.display.button

import ru.capjack.ktjs.app.display.button.BaseTexturesDetail
import ru.capjack.ktjs.app.display.button.ButtonState
import ru.capjack.ktjs.app.display.button.ButtonStateValues
import ru.capjack.ktjs.wrapper.pixi.Sprite
import ru.capjack.ktjs.wrapper.pixi.Texture

open class TexturesDetail(textures: ButtonStateValues<Texture>) : BaseTexturesDetail(textures) {
	override val display = Sprite(textures[ButtonState.DISABLED])
	
	override fun setStateValue(value: Texture) {
		display.texture = value
	}
}
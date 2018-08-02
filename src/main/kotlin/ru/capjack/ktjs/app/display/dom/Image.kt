package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.wrapper.pixi.Sprite
import ru.capjack.ktjs.wrapper.pixi.Texture

class Image(
	texture: Texture = Texture.EMPTY,
	deformable: Boolean = false

) : Tail(deformable) {
	
	override val display = Sprite(texture)
	
	var texture: Texture
		get() = display.texture
		set(value) {
			display.texture = value
			processChangeTexture()
		}
	
	init {
		processChangeTexture()
	}
	
	private fun processChangeTexture() {
		updateContentSizeByDisplay()
		if (texture.baseTexture.hasLoaded) {
			texture.once("update", ::updateContentSizeByDisplay)
		}
	}
}



package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.wrapper.pixi.Sprite
import ru.capjack.ktjs.wrapper.pixi.Texture

class Image(texture: Texture) : Tail() {
	
	override val display = Sprite(texture)
	
	var texture: Texture
		get() = display.texture
		set(value) {
			display.texture = value
			processTextureChange()
		}
	
	init {
		processTextureChange()
	}
	
	private fun processTextureChange() {
		specifyContentSizeByDisplay()
		if (texture.baseTexture.hasLoaded) {
			texture.once("update", ::specifyContentSizeByDisplay)
		}
	}
}



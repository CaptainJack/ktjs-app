package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.app.display.dom.traits.SizeRules
import ru.capjack.ktjs.wrapper.pixi.Sprite
import ru.capjack.ktjs.wrapper.pixi.Texture

class Image(
	texture: Texture = Texture.EMPTY,
	deformable: Boolean = false

) : Tail(deformable) {
	
	override val display = Sprite(texture)
	
	init {
		if (deformable) {
			sizeRule = SizeRules.NOTHING
		}
		processChangeTexture()
	}
	
	var texture: Texture
		get() = display.texture
		set(value) {
			display.texture = value
			processChangeTexture()
		}
	
	private fun processChangeTexture() {
		updateContentSizeByDisplay()
		if (!texture.baseTexture.hasLoaded) {
			texture.once("update", ::updateContentSizeByDisplay)
		}
	}
}



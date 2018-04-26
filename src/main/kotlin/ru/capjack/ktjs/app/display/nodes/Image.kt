package ru.capjack.ktjs.app.display.nodes

import ru.capjack.ktjs.wrapper.pixi.Sprite
import ru.capjack.ktjs.wrapper.pixi.Texture

class Image(texture: Texture) : NodeOfContainer<Sprite>(Sprite(texture)) {
	var texture: Texture
		get() = view.texture
		set(value) {
			view.texture = value
			specifySizeByView()
		}
	
	private var textureLoaded = texture.baseTexture.hasLoaded
	
	init {
		if (!textureLoaded) {
			texture.once("update", ::processTextureLoaded)
		}
	}
	
	override fun assignViewSize() {
		if (textureLoaded) {
			super.assignViewSize()
		}
	}
	
	private fun processTextureLoaded() {
		textureLoaded = true
		if (size.isEquals(1)) {
			specifySizeByView()
		}
		else {
			assignViewSize()
		}
	}
}



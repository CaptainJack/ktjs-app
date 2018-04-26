package ru.capjack.ktjs.app.display.nodes

import ru.capjack.ktjs.common.geom.Insets
import ru.capjack.ktjs.wrapper.pixi.Texture
import ru.capjack.ktjs.wrapper.pixi.mesh.NineSlicePlane

class NineSlicedImage(texture: Texture, insets: Insets<Int>) : NodeOfContainer<NineSlicePlane>(
	NineSlicePlane(
		texture,
		insets.left,
		insets.top,
		insets.right,
		insets.bottom
	)
) {
	
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



package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.wrapper.pixi.BaseTexture
import ru.capjack.ktjs.wrapper.pixi.Texture

internal class ImageAssetImpl : AbstractAsset(), ImageAsset {
	override val texture: Texture
		get() {
			checkAvailable()
			if (_texture == null) {
				_texture = Texture.from(image)
			}
			return _texture!!
		}
	
	private var _texture: Texture? = null
	
	private lateinit var image: BaseTexture
	
	fun load(image: BaseTexture) {
		this.image = image
		completeLoad()
	}
	
	override fun doDestroy() {
		_texture?.destroy(false)
		image.destroy()
	}
}
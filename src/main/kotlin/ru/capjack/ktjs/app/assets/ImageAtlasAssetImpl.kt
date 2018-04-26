package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.wrapper.pixi.BaseTexture
import ru.capjack.ktjs.wrapper.pixi.Texture

internal class ImageAtlasAssetImpl : AbstractAsset(), ImageAtlasAsset {
	override val textures: List<Texture>
		get() = atlasTextures.map(ImageAtlasTexture::texture)
	
	private lateinit var image: BaseTexture
	private lateinit var atlasTextures: List<ImageAtlasTexture>
	
	override fun get(name: String): Texture {
		return atlasTextures.first { it.name == name }.texture
	}
	
	override fun doDestroy() {
		atlasTextures.forEach { it.texture.destroy(false) }
		image.destroy()
	}
	
	fun load(image: BaseTexture, textures: List<ImageAtlasTexture>) {
		this.image = image
		this.atlasTextures = textures
	}
}
package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.sound.Sound
import ru.capjack.ktjs.wrapper.pixi.Texture

internal class AssetsImpl(
	private val images: Map<String, ImageAssetImpl>,
	private val imageAtlases: Map<String, ImageAtlasAssetImpl>,
	private val sounds: Map<String, SoundAssetImpl>
) : Assets {
	
	override fun getImageAsset(name: String): ImageAsset {
		return fetchAsset(images, name)
	}
	
	override fun getAtlasAsset(name: String): ImageAtlasAsset {
		return fetchAsset(imageAtlases, name)
	}
	
	override fun getSoundAsset(name: String): SoundAsset {
		return fetchAsset(sounds, name)
	}
	
	override fun getTexture(name: String): Texture {
		return getImageAsset(name).texture
	}
	
	override fun getTexture(atlasName: String, frameName: String): Texture {
		return getAtlasAsset(atlasName)[frameName]
	}
	
	override fun getSound(name: String): Sound {
		return getSoundAsset(name).sound
	}
	
	private fun <A : Asset> fetchAsset(map: Map<String, A>, name: String): A {
		val asset = map[name]
		return asset ?: throw IllegalArgumentException("Asset named \"$name\" is not exist")
	}
	
	override fun destroy() {
		for (asset in images.values) {
			asset.destroy()
		}
	}
}


package ru.capjack.ktjs.app.assets

import org.w3c.dom.Document
import org.w3c.dom.HTMLVideoElement
import ru.capjack.ktjs.app.sound.Sound
import ru.capjack.ktjs.wrapper.pixi.Texture

internal class AssetsImpl(
	private val images: Map<String, ImageAssetImpl>,
	private val imageAtlases: Map<String, ImageAtlasAssetImpl>,
	private val sounds: Map<String, SoundAssetImpl>,
	private val xmls: Map<String, XmlAssetImpl>,
	private val videos: Map<String, VideoAssetImpl>
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
	
	override fun getXmlAsset(name: String): XmlAsset {
		return fetchAsset(xmls, name)
	}
	
	override fun getVideoAsset(name: String): VideoAsset {
		return fetchAsset(videos, name)
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
	
	override fun getXml(name: String): Document {
		return getXmlAsset(name).document
	}
	
	override fun getVideo(name: String): HTMLVideoElement {
		return getVideoAsset(name).video
	}
	
	override fun destroy() {
		for (asset in images.values) {
			asset.destroy()
		}
	}
	
	private fun <A : Asset> fetchAsset(map: Map<String, A>, name: String): A {
		val asset = map[name]
		return asset ?: throw IllegalArgumentException("Asset named \"$name\" is not exist")
	}
}


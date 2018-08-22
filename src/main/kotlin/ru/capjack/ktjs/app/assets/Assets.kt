package ru.capjack.ktjs.app.assets

import org.w3c.dom.Document
import org.w3c.dom.HTMLVideoElement
import ru.capjack.ktjs.app.sound.Sound
import ru.capjack.ktjs.common.Destroyable
import ru.capjack.ktjs.wrapper.pixi.Texture

interface Assets : Destroyable {
	fun getImageAsset(name: String): ImageAsset
	
	fun getSvgAsset(name: String): SvgAsset
	
	fun getAtlasAsset(name: String): ImageAtlasAsset
	
	fun getSoundAsset(name: String): SoundAsset
	
	fun getXmlAsset(name: String): XmlAsset
	
	fun getVideoAsset(name: String): VideoAsset
	
	fun getJsonAsset(name: String): JsonAsset
	
	fun getTexture(name: String): Texture
	
	fun getTexture(atlasName: String, frameName: String): Texture
	
	fun getSvgTexture(name: String, width: Int, height: Int): Texture
	
	fun getSound(name: String): Sound
	
	fun getXml(name: String): Document
	
	fun getVideo(name: String): HTMLVideoElement
	
	fun getJson(name: String): dynamic
}

package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.sound.Sound
import ru.capjack.ktjs.common.Destroyable
import ru.capjack.ktjs.wrapper.pixi.Texture

interface Assets : Destroyable {
	fun getImageAsset(name: String): ImageAsset
	
	fun getAtlasAsset(name: String): ImageAtlasAsset
	
	fun getSoundAsset(name: String): SoundAsset
	
	fun getTexture(name: String): Texture
	
	fun getTexture(atlasName: String, frameName: String): Texture
	
	fun getSound(name: String): Sound
}

package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.assets.font.FontFace
import ru.capjack.ktjs.common.rl.FilePath

interface AssetsCollector {
	fun addImage(name: String): ImageAsset
	
	fun addImage(name: String, path: String): ImageAsset
	
	fun addImage(name: String, path: FilePath): ImageAsset
	
	fun addImages(vararg names: String)
	
	fun addImages(names: Collection<String>)
	
	fun addImageAtlas(name: String): ImageAtlasAsset
	
	fun addImageAtlas(name: String, path: String): ImageAtlasAsset
	
	fun addImageAtlas(name: String, path: FilePath): ImageAtlasAsset
	
	fun addFont(face: FontFace): FontAsset
	
	fun addFonts(vararg fonts: FontFace)
	
	fun addFonts(fonts: Collection<FontFace>)
	
	fun addSound(name: String): SoundAsset
	
	fun addSound(name: String, path: String): SoundAsset
	
	fun addSound(name: String, path: FilePath): SoundAsset
	
	fun addSounds(vararg names: String)
	
	fun addSounds(names: Collection<String>)
	
	fun inDirectory(dir: String): AssetsCollector
}


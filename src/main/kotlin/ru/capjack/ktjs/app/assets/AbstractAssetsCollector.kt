package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.assets.font.FontFace
import ru.capjack.ktjs.common.rl.FilePaths

internal abstract class AbstractAssetsCollector : AssetsCollector {
	override fun addImage(name: String): ImageAsset {
		return addImage(name, name)
	}
	
	override fun addImage(name: String, path: String): ImageAsset {
		return addImage(name, FilePaths.get(path))
	}
	
	override fun addImages(vararg names: String) {
		names.forEach { addImage(it) }
	}
	
	override fun addImages(names: Collection<String>) {
		names.forEach { addImage(it) }
	}
	
	override fun addImageAtlas(name: String): ImageAtlasAsset {
		return addImageAtlas(name, name)
	}
	
	override fun addImageAtlas(name: String, path: String): ImageAtlasAsset {
		return addImageAtlas(name, FilePaths.get(path))
	}
	
	override fun addFonts(vararg fonts: FontFace) {
		fonts.forEach { addFont(it) }
	}
	
	override fun addFonts(fonts: Collection<FontFace>) {
		fonts.forEach { addFont(it) }
	}
	
	override fun addSound(name: String): SoundAsset {
		return addSound(name, name)
	}
	
	override fun addSound(name: String, path: String): SoundAsset {
		return addSound(name, FilePaths.get(path))
	}
	
	override fun addSounds(vararg names: String) {
		names.forEach { addSound(it) }
	}
	
	override fun addSounds(names: Collection<String>) {
		names.forEach { addSound(it) }
	}
}
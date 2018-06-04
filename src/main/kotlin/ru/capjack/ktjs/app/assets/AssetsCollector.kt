package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.assets.font.FontFace
import ru.capjack.ktjs.common.rl.FilePath
import ru.capjack.ktjs.common.rl.FilePaths

interface AssetsCollector {
	fun addFont(face: FontFace): FontAsset
	
	fun addImage(name: String, path: FilePath = FilePaths.get(name)): ImageAsset
	
	fun addImageAtlas(name: String, path: FilePath = FilePaths.get(name)): ImageAtlasAsset
	
	fun addSound(name: String, path: FilePath = FilePaths.get(name)): SoundAsset
}


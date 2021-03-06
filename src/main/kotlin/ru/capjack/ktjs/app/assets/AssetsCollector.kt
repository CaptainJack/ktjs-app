package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.assets.font.FontFace
import ru.capjack.ktjs.common.rl.FilePath
import ru.capjack.ktjs.common.rl.FilePaths

interface AssetsCollector {
	fun addFont(face: FontFace): FontAsset
	
	fun addImage(name: String, path: FilePath = FilePaths.get(name)): ImageAsset
	
	fun addSvg(name: String, path: FilePath = FilePaths.get(name)): SvgAsset
	
	fun addImageAtlas(name: String, path: FilePath = FilePaths.get(name)): ImageAtlasAsset
	
	fun addSound(name: String, path: FilePath = FilePaths.get(name)): SoundAsset
	
	fun addXml(name: String, path: FilePath = FilePaths.get(name)): XmlAsset
	
	fun addVideo(name: String, path: FilePath = FilePaths.get(name)): VideoAsset
	
	fun addJson(name: String, path: FilePath = FilePaths.get(name)): JsonAsset
}


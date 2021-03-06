package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.assets.font.FontFace
import ru.capjack.ktjs.common.rl.FilePath

internal class DirectoryAssetsCollector(
	private val target: AssetsCollector,
	private val dir: FilePath
) : AssetsCollector {
	override fun addImage(name: String, path: FilePath): ImageAsset {
		return target.addImage(dir.valueAsDirectory + name, dir.resolve(path))
	}
	
	override fun addSvg(name: String, path: FilePath): SvgAsset {
		return target.addSvg(dir.valueAsDirectory + name, dir.resolve(path))
	}
	
	override fun addImageAtlas(name: String, path: FilePath): ImageAtlasAsset {
		return target.addImageAtlas(dir.valueAsDirectory + name, dir.resolve(path))
	}
	
	override fun addFont(face: FontFace): FontAsset {
		return target.addFont(face)
	}
	
	override fun addSound(name: String, path: FilePath): SoundAsset {
		return target.addSound(dir.valueAsDirectory + name, dir.resolve(path))
	}
	
	override fun addXml(name: String, path: FilePath): XmlAsset {
		return target.addXml(dir.valueAsDirectory + name, dir.resolve(path))
	}
	
	override fun addVideo(name: String, path: FilePath): VideoAsset {
		return target.addVideo(dir.valueAsDirectory + name, dir.resolve(path))
	}
	
	override fun addJson(name: String, path: FilePath): JsonAsset {
		return target.addJson(dir.valueAsDirectory + name, dir.resolve(path))
	}
}
package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.assets.font.FontFace
import ru.capjack.ktjs.common.rl.FilePaths

fun AssetsCollector.dir(path: String): AssetsCollector {
	return DirectoryAssetsCollector(this, FilePaths.get(path))
}

fun AssetsCollector.dir(path: String, block: AssetsCollector.() -> Unit) {
	dir(path).block()
}

fun AssetsCollector.addImage(name: String, path: String): ImageAsset {
	return addImage(name, FilePaths.get(path))
}

fun AssetsCollector.addSvg(name: String, path: String): SvgAsset {
	return addSvg(name, FilePaths.get(path))
}

fun AssetsCollector.addImages(vararg names: String) {
	names.forEach { addImage(it) }
}

fun AssetsCollector.addImageAtlas(name: String, path: String): ImageAtlasAsset {
	return addImageAtlas(name, FilePaths.get(path))
}

fun AssetsCollector.addFonts(vararg fonts: FontFace) {
	fonts.forEach { addFont(it) }
}

fun AssetsCollector.addSound(name: String, path: String): SoundAsset {
	return addSound(name, FilePaths.get(path))
}

fun AssetsCollector.addSounds(vararg names: String) {
	names.forEach { addSound(it) }
}

fun AssetsCollector.addXml(name: String, path: String): XmlAsset {
	return addXml(name, FilePaths.get(path))
}

fun AssetsCollector.addVideo(name: String, path: String): VideoAsset {
	return addVideo(name, FilePaths.get(path))
}

fun AssetsCollector.addVideos(vararg names: String) {
	names.forEach { addVideo(it) }
}

fun AssetsCollector.addJson(name: String, path: String): JsonAsset {
	return addJson(name, FilePaths.get(path))
}
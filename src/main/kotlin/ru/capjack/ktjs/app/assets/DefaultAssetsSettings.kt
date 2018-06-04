package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.common.rl.FilePath

class DefaultAssetsSettings(
	override val bitmapImageResolution: Int,
	private val bitmapResolutionResolveSuffix: Boolean
) : AssetsSettings {
	
	override fun convertImagePath(path: FilePath): FilePath = when {
		!path.isFileExtension("svg") && bitmapResolutionResolveSuffix && bitmapImageResolution != 1
		     -> path.resolveSibling("${path.name.base}@${bitmapImageResolution}x${path.name.extensionAsSuffix}")
		else -> path
	}
}
package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.common.rl.FilePath

data class AssetsSettings(
	val imageResolution: Int,
	private val bitmapResolutionResolveSuffix: Boolean
) {
	
	fun resolveImagePath(path: FilePath): FilePath = when {
		path.isFileExtension("svg") && bitmapResolutionResolveSuffix && imageResolution != 1
		     -> path.resolveSibling("${path.name.base}@${imageResolution}x${path.name.extensionAsSuffix}")
		else -> path
	}
}

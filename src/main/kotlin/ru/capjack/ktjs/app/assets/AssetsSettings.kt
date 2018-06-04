package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.common.rl.FilePath

interface AssetsSettings {
	val bitmapImageResolution: Int
	
	fun convertImagePath(path: FilePath): FilePath
}
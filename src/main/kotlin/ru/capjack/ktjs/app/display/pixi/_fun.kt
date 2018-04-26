package ru.capjack.ktjs.app.display.pixi

import ru.capjack.ktjs.app.assets.ImageAtlasAsset
import ru.capjack.ktjs.app.display.button.ButtonStateValues
import ru.capjack.ktjs.wrapper.pixi.Texture

fun getAtlasButtonTextures(atlas: ImageAtlasAsset, baseName: String): ButtonStateValues<Texture> {
	return ButtonStateValues(
		atlas["$baseName-idle"],
		atlas["$baseName-focus"],
		atlas["$baseName-active"],
		atlas["$baseName-disabled"]
	)
}
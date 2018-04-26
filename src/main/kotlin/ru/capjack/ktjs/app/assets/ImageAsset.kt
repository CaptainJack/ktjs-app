package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.wrapper.pixi.Texture

interface ImageAsset : Asset {
	val texture: Texture
}
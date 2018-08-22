package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.wrapper.pixi.Texture

interface SvgAsset : Asset {
	val texture: Texture
	
	fun getTexture(width: Int, height: Int): Texture
}
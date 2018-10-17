package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.wrapper.pixi.Texture

interface ImageAtlasAsset : Asset {
	val textures: List<Texture>
	
	operator fun get(name: String): Texture
}
package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.wrapper.pixi.DisplayObject
import ru.capjack.ktjs.wrapper.pixi.RenderTexture

interface DisplayRenderer {
	fun resize(width: Int, height: Int)
	
	fun render(display: DisplayObject)
	
	fun render(display: DisplayObject, texture: RenderTexture)
}

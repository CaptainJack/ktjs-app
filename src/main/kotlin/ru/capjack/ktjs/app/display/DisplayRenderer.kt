package ru.capjack.ktjs.app.display

import org.w3c.dom.HTMLCanvasElement
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.wrapper.pixi.DisplayObject
import ru.capjack.ktjs.wrapper.pixi.RenderTexture

interface DisplayRenderer {
	val canvas: HTMLCanvasElement
	val pixelRatio: Double
	val resolution: Double
	val bitmapImageResolution: Int
	
	fun resize(size: AxialValues<Int>)
	
	fun render(display: DisplayObject)
	
	fun render(display: DisplayObject, texture: RenderTexture)
}

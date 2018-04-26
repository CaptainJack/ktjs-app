package ru.capjack.ktjs.app.display

import org.w3c.dom.HTMLCanvasElement
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.js.jst
import ru.capjack.ktjs.wrapper.pixi.DisplayObject
import ru.capjack.ktjs.wrapper.pixi.RenderTexture
import ru.capjack.ktjs.wrapper.pixi.SystemRenderer
import ru.capjack.ktjs.wrapper.pixi.autoDetectRenderer
import ru.capjack.ktjs.wrapper.pixi.utils.skipHello

class DisplayRendererImpl(size: AxialValues<Int>, rendererResolution: Double, canvas: HTMLCanvasElement) : DisplayRenderer {
	
	private var source: SystemRenderer = autoDetectRenderer(jst {
		skipHello()
		width = size.horizontal
		height = size.vertical
		resolution = rendererResolution
		backgroundColor = 0x666666
		antialias = true
		view = canvas
	})
	
	override fun resize(width: Int, height: Int) {
		source.resize(width, height)
	}
	
	override fun render(display: DisplayObject) {
		source.render(display)
	}
	
	override fun render(display: DisplayObject, texture: RenderTexture) {
		source.render(display, texture)
	}
}

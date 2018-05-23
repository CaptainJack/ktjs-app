package ru.capjack.ktjs.app.display

import org.w3c.dom.HTMLCanvasElement
import ru.capjack.ktjs.app.display.resolution.ResolutionResolver
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.js.jso
import ru.capjack.ktjs.common.replaceIfNotFinite
import ru.capjack.ktjs.wrapper.pixi.DisplayObject
import ru.capjack.ktjs.wrapper.pixi.RenderTexture
import ru.capjack.ktjs.wrapper.pixi.SystemRenderer
import ru.capjack.ktjs.wrapper.pixi.autoDetectRenderer
import ru.capjack.ktjs.wrapper.pixi.utils.skipHello
import kotlin.browser.document
import kotlin.browser.window

class DisplayRendererImpl(size: AxialValues<Int>, resolutionResolver: ResolutionResolver) : DisplayRenderer {
	
	override val canvas: HTMLCanvasElement = document.createElement("canvas") as HTMLCanvasElement
	override val pixelRatio: Double = window.devicePixelRatio.replaceIfNotFinite(1.0)
	override val resolution: Double = resolutionResolver.resolveRendererResolution(pixelRatio)
	override val bitmapImageResolution: Int = resolutionResolver.resolveBitmapImageResolution(resolution)
	
	private var pixiRenderer: SystemRenderer = autoDetectRenderer(jso {
		skipHello()
		width = size.x
		height = size.y
		resolution = this@DisplayRendererImpl.resolution
		backgroundColor = 0x666666
		antialias = true
		view = canvas
	})
	
	
	override fun resize(screenSize: AxialValues<Int>, canvasSize: AxialValues<Int>) {
		pixiRenderer.resize(screenSize.x, screenSize.y)
		canvas.style.width = "${canvasSize.x}px"
		canvas.style.height = "${canvasSize.y}px"
	}
	
	override fun render(display: DisplayObject) {
		pixiRenderer.render(display)
	}
	
	override fun render(display: DisplayObject, texture: RenderTexture) {
		pixiRenderer.render(display, texture)
	}
}

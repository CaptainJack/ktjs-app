package ru.capjack.ktjs.app.display

import org.w3c.dom.HTMLCanvasElement
import ru.capjack.ktjs.app.display.resolution.ResolutionResolver
import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.js.jso
import ru.capjack.ktjs.common.replaceIf
import ru.capjack.ktjs.wrapper.pixi.DisplayObject
import ru.capjack.ktjs.wrapper.pixi.RenderTexture
import ru.capjack.ktjs.wrapper.pixi.SystemRenderer
import ru.capjack.ktjs.wrapper.pixi.autoDetectRenderer
import ru.capjack.ktjs.wrapper.pixi.utils.skipHello
import kotlin.browser.document
import kotlin.browser.window

class DisplayRendererImpl(size: Axial<Int>, resolutionResolver: ResolutionResolver, color: Int) : DisplayRenderer {
	
	override val canvas: HTMLCanvasElement = document.createElement("canvas") as HTMLCanvasElement
	override val pixelRatio: Double = window.devicePixelRatio.replaceIf({ !it.isFinite() }, 1.0)
	override val resolution: Double = resolutionResolver.resolveRendererResolution(pixelRatio)
	override val bitmapImageResolution: Int = resolutionResolver.resolveBitmapImageResolution(resolution)
	
	private var pixiRenderer: SystemRenderer = autoDetectRenderer(jso {
		skipHello()
		width = size.x
		height = size.y
		resolution = this@DisplayRendererImpl.resolution
		backgroundColor = color
		antialias = true
		view = canvas
	})
	
	override fun resize(size: Axial<Int>) {
		pixiRenderer.resize(size.x, size.y)
		canvas.style.width = "${size.x}px"
		canvas.style.height = "${size.y}px"
	}
	
	override fun render(display: DisplayObject) {
		pixiRenderer.render(display)
	}
	
	override fun render(display: DisplayObject, texture: RenderTexture) {
		pixiRenderer.render(display, texture)
	}
}

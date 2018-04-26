package ru.capjack.ktjs.app.assets

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.Event
import ru.capjack.ktjs.app.display.DisplayRenderer
import ru.capjack.ktjs.common.rl.Url
import ru.capjack.ktjs.wrapper.pixi.BaseRenderTexture
import ru.capjack.ktjs.wrapper.pixi.BaseTexture
import ru.capjack.ktjs.wrapper.pixi.Container
import ru.capjack.ktjs.wrapper.pixi.Rectangle
import ru.capjack.ktjs.wrapper.pixi.RenderTexture
import ru.capjack.ktjs.wrapper.pixi.Sprite
import ru.capjack.ktjs.wrapper.pixi.Texture
import kotlin.browser.document

class ImageLoader(
	private val url: Url,
	private val renderer: DisplayRenderer,
	private val settings: AssetsSettings,
	private val receiver: (BaseTexture) -> Unit
) {
	
	private var image: dynamic = js("new Image()")
	
	init {
		image.onload = ::processOnLoad
		image.onerror = ::processOnError
		image.src = url.value
	}
	
	private fun processOnLoad(@Suppress("UNUSED_PARAMETER") event: Event) {
		releaseImage()
		
		val fileName = url.path.name
		
		val texture =
			if (fileName.extension == "svg") {
				createSvgTexture()
			}
			else if (fileName.extension == "jpg" && (fileName.base.endsWith(".ah") || fileName.base.endsWith(".av"))) {
				createJpgaTexture()
			}
			else {
				BaseTexture(image, resolution = settings.imageResolution)
			}
		
		receiver.invoke(texture)
	}
	
	private fun createSvgTexture(): BaseTexture {
		val canvas = document.createElement("canvas") as HTMLCanvasElement
		val width = image.naturalWidth as Int
		val height = image.naturalHeight as Int
		
		canvas.width = width * settings.imageResolution
		canvas.height = height * settings.imageResolution
		
		(canvas.getContext("2d") as CanvasRenderingContext2D).drawImage(
			image,
			0.0, 0.0, width.toDouble(), height.toDouble(),
			0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble()
		)
		
		return BaseTexture(canvas, resolution = settings.imageResolution)
	}
	
	private fun createJpgaTexture(): BaseTexture {
		val h = url.path.name.base.endsWith(".ah")
		
		val width = image.naturalWidth as Double / settings.imageResolution
		val height = image.naturalHeight as Double / settings.imageResolution
		val frameWidth = if (h) width / 2 else width
		val frameHeight = if (h) height else height / 2
		
		val sourceTextureBase = BaseTexture(image, resolution = settings.imageResolution)
		val renderedTextureBase = BaseRenderTexture(frameWidth, frameHeight, resolution = settings.imageResolution)
		val renderedTexture = RenderTexture(renderedTextureBase)
		
		val container = Container()
		val sprite = Sprite(Texture(sourceTextureBase, Rectangle(0, 0, frameWidth, frameHeight)))
		val mask = Sprite(Texture(sourceTextureBase, Rectangle(if (h) frameWidth else 0, if (h) 0 else frameHeight, frameWidth, frameHeight)))
		
		container.addChild(sprite)
		container.addChild(mask)
		sprite.mask = mask
		
		renderer.render(container, renderedTexture)
		
		container.destroy(true)
		renderedTexture.destroy(false)
		
		return renderedTextureBase
	}
	
	private fun processOnError(event: Event) {
		releaseImage()
		throw RuntimeException("Failed to load image \"$url\" ($event)")
	}
	
	private fun releaseImage() {
		image.onload = null
		image.onerror = null
	}
}

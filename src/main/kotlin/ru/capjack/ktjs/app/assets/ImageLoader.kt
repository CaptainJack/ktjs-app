package ru.capjack.ktjs.app.assets

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLImageElement
import org.w3c.dom.events.Event
import ru.capjack.ktjs.app.display.DisplayRenderer
import ru.capjack.ktjs.common.js.jso
import ru.capjack.ktjs.common.rl.Url
import ru.capjack.ktjs.wrapper.pixi.BaseRenderTexture
import ru.capjack.ktjs.wrapper.pixi.BaseTexture
import ru.capjack.ktjs.wrapper.pixi.Container
import ru.capjack.ktjs.wrapper.pixi.Rectangle
import ru.capjack.ktjs.wrapper.pixi.RenderTexture
import ru.capjack.ktjs.wrapper.pixi.Sprite
import ru.capjack.ktjs.wrapper.pixi.SystemRenderer
import ru.capjack.ktjs.wrapper.pixi.Texture
import ru.capjack.ktjs.wrapper.pixi.autoDetectRenderer
import kotlin.browser.document

class ImageLoader(
	private val url: Url,
	private val renderer: DisplayRenderer,
	private val settings: AssetsSettings,
	private val receiver: (BaseTexture) -> Unit
) {
	
	private var image: HTMLImageElement = js("new Image()").unsafeCast<HTMLImageElement>()
	
	init {
		image.onload = ::processOnLoad
		image.onerror = ::processOnError
		image.src = url.value
	}
	
	@Suppress("UNUSED_PARAMETER")
	private fun processOnLoad(event: Event) {
		releaseImage()
		
		val fileName = url.path.name
		
		val texture =
			if (fileName.extension == "svg") {
				createSvgTexture()
			}
			else if (fileName.extension == "jpg" && fileName.base.substringBeforeLast('@').run { endsWith(".ah") || endsWith(".av") }) {
				createJpgaTexture()
			}
			else {
				BaseTexture(image, resolution = settings.imageResolution)
			}
		
		receiver.invoke(texture)
	}
	
	private fun createSvgTexture(): BaseTexture {
		val canvas = document.createElement("canvas") as HTMLCanvasElement
		
		canvas.width = image.naturalWidth * settings.imageResolution
		canvas.height = image.naturalHeight * settings.imageResolution
		
		(canvas.getContext("2d") as CanvasRenderingContext2D)
			.drawImage(image, 0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
		
		return BaseTexture(canvas, resolution = settings.imageResolution)
	}
	
	private fun createJpgaTexture(): BaseTexture {
		val h = url.path.name.base.substringBeforeLast('@').endsWith(".ah")
		
		val sourceWidth = image.naturalWidth / settings.imageResolution
		val sourceHeight = image.naturalHeight / settings.imageResolution
		val frameWidth = if (h) sourceWidth / 2 else sourceWidth
		val frameHeight = if (h) sourceHeight else sourceHeight / 2
		
		val source = BaseTexture(image, resolution = settings.imageResolution)
		
		val container = Container()
		val spriteImage = Sprite(Texture(source, Rectangle(0, 0, frameWidth, frameHeight)))
		val spriteMask = Sprite(Texture(source, Rectangle(if (h) frameWidth else 0, if (h) 0 else frameHeight, frameWidth, frameHeight)))
		
		container.addChild(spriteImage)
		container.addChild(spriteMask)
		spriteImage.mask = spriteMask
		
		val result: BaseTexture
		
		if (renderer.bitmapImageResolution == settings.imageResolution) {
			result = BaseRenderTexture(frameWidth, frameHeight, resolution = settings.imageResolution)
			val texture = RenderTexture(result)
			renderer.render(container, texture)
			texture.destroy(false)
		}
		else {
			val r = customRenderers.getOrPut(settings.imageResolution) {
				autoDetectRenderer(jso {
					width = frameWidth
					height = frameHeight
					resolution = settings.imageResolution.toDouble()
					antialias = true
					transparent = true
				})
			}
			
			r.resize(frameWidth, frameHeight)
			r.render(container)
			
			val img = js("new Image()").unsafeCast<HTMLImageElement>()
			img.src = r.view.toDataURL()
			
			result = BaseTexture(img, resolution = settings.imageResolution)
		}
		
		container.destroy(true)
		source.destroy()
		
		return result
	}
	
	@Suppress("UNUSED_PARAMETER")
	private fun processOnError(message: dynamic, source: String, line: Int, col: Int, error: Any?) {
		releaseImage()
		throw FileLoadFailException(url, message)
	}
	
	private fun releaseImage() {
		image.onload = null
		image.onerror = null
	}
	
	companion object {
		val customRenderers = mutableMapOf<Int, SystemRenderer>()
	}
}


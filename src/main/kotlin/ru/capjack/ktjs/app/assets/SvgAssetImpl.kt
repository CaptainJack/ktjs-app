package ru.capjack.ktjs.app.assets

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLImageElement
import ru.capjack.ktjs.wrapper.pixi.BaseTexture
import ru.capjack.ktjs.wrapper.pixi.Texture
import kotlin.browser.document

internal class SvgAssetImpl(private val imageResolution: Int) : AbstractAsset(), SvgAsset {
	private val textures = mutableMapOf<String, Texture>()
	private lateinit var image: HTMLImageElement
	
	override val texture: Texture
		get() {
			checkAvailable()
			return getTexture(image.naturalWidth, image.naturalHeight)
		}
	
	override fun getTexture(width: Int, height: Int): Texture {
		checkAvailable()
		
		return textures.getOrPut("${width}x$height") {
			val canvas = document.createElement("canvas") as HTMLCanvasElement
			
			canvas.width = width * imageResolution
			canvas.height = height * imageResolution
			
			(canvas.getContext("2d") as CanvasRenderingContext2D)
				.drawImage(image, 0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
			
			Texture(BaseTexture(canvas, resolution = imageResolution))
		}
	}
	
	override fun doDestroy() {
		textures.values.forEach { it.destroy(true) }
		textures.clear()
	}
	
	fun load(image: HTMLImageElement) {
		this.image = image
		completeLoad()
	}
}

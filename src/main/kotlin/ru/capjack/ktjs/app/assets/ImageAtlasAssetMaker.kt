package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.display.DisplayRenderer
import ru.capjack.ktjs.common.rl.Url
import ru.capjack.ktjs.wrapper.pixi.BaseTexture
import ru.capjack.ktjs.wrapper.pixi.Rectangle
import ru.capjack.ktjs.wrapper.pixi.Texture
import ru.capjack.ktjs.wrapper.pixi.pixi

internal class ImageAtlasAssetMaker(
	url: Url,
	private val renderer: DisplayRenderer,
	private val settings: AssetsSettings
) : AbstractAssetMaker<ImageAtlasAssetImpl>(ImageAtlasAssetImpl(), url) {
	private lateinit var data: ImageAtlasData
	
	override fun doRun() {
		ImageAtlasDataLoader(url, ::receiveData)
	}
	
	private fun receiveData(data: ImageAtlasData) {
		this.data = data
		
		var path = data.imagePath
		
		if (path.startsWith('.')) {
			path = url.path.name.base + path
		}
		
		loadImage(path)
	}
	
	private fun loadImage(path: String) {
		val url = url.resolvePathSibling(path)
		ImageLoader(url, renderer, settings, ::receiveImage)
	}
	
	private fun receiveImage(image: BaseTexture) {
//		val resolution = if (data.imagePath.endsWith(".svg")) 1.0 else settings.imageResolution.toDouble()
		val textures: MutableList<ImageAtlasTexture> = mutableListOf()
		
		for (item in data.items) {
			val frame = item.frame.pixi
			val trim = if (item.trimmed) {
				if (item.rotated) Rectangle(item.offset.x, item.offset.y, item.frame.height, item.frame.width)
				else Rectangle(item.offset.x, item.offset.y, item.frame.width, item.frame.height)
			}
			else null
			
			textures.add(
				ImageAtlasTexture(
					item.name,
					Texture(
						image,
						frame,
						Rectangle(0, 0, item.size.x, item.size.y),
						trim,
						if (item.rotated) 2 else 0
					)
				)
			)
		}
		
		asset.load(image, textures)
		complete()
	}
}

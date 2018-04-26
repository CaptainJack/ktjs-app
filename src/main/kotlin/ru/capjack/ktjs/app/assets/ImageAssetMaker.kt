package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.display.DisplayRenderer
import ru.capjack.ktjs.common.rl.Url
import ru.capjack.ktjs.wrapper.pixi.BaseTexture

internal class ImageAssetMaker(
	url: Url,
	private val renderer: DisplayRenderer,
	private val settings: AssetsSettings
) : AbstractAssetMaker<ImageAssetImpl>(ImageAssetImpl(), url) {
	override fun doRun() {
		ImageLoader(url, renderer, settings, ::receiveImage)
	}
	
	private fun receiveImage(image: BaseTexture) {
		asset.load(image)
		complete()
	}
}
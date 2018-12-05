package ru.capjack.ktjs.app.assets

import org.w3c.dom.HTMLImageElement
import org.w3c.dom.events.Event
import ru.capjack.ktjs.common.rl.Url

internal class SvgAssetMaker(
	url: Url,
	settings: AssetsSettings
) : AbstractAssetMaker<SvgAssetImpl>(SvgAssetImpl(settings.imageResolution), url) {
	
	private var image: HTMLImageElement = js("new Image()").unsafeCast<HTMLImageElement>()
	
	override fun doRun() {
		image.onload = ::processOnLoad
		image.onerror = ::processOnError
		image.src = url.value
	}
	
	@Suppress("UNUSED_PARAMETER")
	private fun processOnLoad(event: Event) {
		releaseImage()
		asset.load(image)
		complete()
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
}
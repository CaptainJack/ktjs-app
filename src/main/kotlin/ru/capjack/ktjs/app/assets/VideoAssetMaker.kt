package ru.capjack.ktjs.app.assets

import org.w3c.dom.HTMLVideoElement
import org.w3c.dom.events.Event
import ru.capjack.ktjs.common.rl.Url
import kotlin.browser.document

internal class VideoAssetMaker(url: Url) : AbstractAssetMaker<VideoAssetImpl>(VideoAssetImpl(), url) {
	
	private val video: HTMLVideoElement = document.createElement("video").unsafeCast<HTMLVideoElement>().apply {
		setAttribute("webkit-playsinline", "")
		setAttribute("playsinline", "")
		playsInline = true
		preload = "auto"
		autoplay = false
	}
	
	override fun doRun() {
		video.src = url.value
		video.load()
	}
	
	init {
		video.oncanplaythrough = ::processOnLoad
		video.onerror = ::processOnError
	}
	
	@Suppress("UNUSED_PARAMETER")
	private fun processOnLoad(event: Event) {
		releaseVideo()
		asset.load(video)
		complete()
	}

	@Suppress("UNUSED_PARAMETER")
	private fun processOnError(message: dynamic, source: String, line: Int, col: Int, error: Any?) {
		releaseVideo()
		throw RuntimeException("Failed to load video \"$url\" ($message)")
	}
	
	private fun releaseVideo() {
		video.onload = null
		video.onerror = null
	}
}
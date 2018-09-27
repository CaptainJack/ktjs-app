package ru.capjack.ktjs.app.assets

import org.w3c.dom.HTMLVideoElement

internal class VideoAssetImpl : AbstractAsset(), VideoAsset {
	override val video: HTMLVideoElement
		get() {
			checkAvailable()
			return _video!!
		}
	
	private var _video: HTMLVideoElement? = null
	
	fun load(video: HTMLVideoElement) {
		_video = video
		completeLoad()
	}
	
	override fun doDestroy() {
		_video?.apply {
			pause()
			src = ""
			load()
			remove()
		}
		_video = null
	}
}

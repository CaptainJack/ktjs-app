package ru.capjack.ktjs.app.assets

internal class JsonAssetImpl : AbstractAsset(), JsonAsset {
	override val content: dynamic
		get() {
			checkAvailable()
			return _content!!
		}
	
	private var _content: dynamic = null
	
	fun load(content: dynamic) {
		_content = content
		completeLoad()
	}
	
	override fun doDestroy() {
		_content = null
	}
}

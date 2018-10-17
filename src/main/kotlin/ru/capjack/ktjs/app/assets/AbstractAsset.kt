package ru.capjack.ktjs.app.assets

internal abstract class AbstractAsset : Asset {
	
	final override var loaded: Boolean = false
		private set
	
	private var destroyed: Boolean = false
	
	protected fun checkAvailable() {
		if (destroyed) {
			throw IllegalStateException("Asset is destroyed")
		}
		if (!loaded) {
			throw IllegalStateException("Asset is not loaded yet")
		}
	}
	
	protected fun completeLoad() {
		loaded = true
	}
	
	override fun destroy() {
		if (loaded && !destroyed) {
			doDestroy()
		}
	}
	
	protected abstract fun doDestroy()
}
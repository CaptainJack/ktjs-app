package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.common.ChangeableObject

class DisplayFullsrceenImpl(
	displaySystem: DisplaySystem
) : DisplayFullsrceen, ChangeableObject<Boolean>() {
	
	override var enabled: Boolean
		get() = delegate.enabled
		set(value) {
			if (enabled != value) {
				makeEnabled(value)
			}
		}
	
	private val delegate: Delegate
	
	init {
		val canvas = displaySystem.renderer.canvas.asDynamic()
		val document = canvas.ownerDocument!!
		
		delegate = when {
			canvas.requestFullscreen != null       -> {
				RealDelegate(canvas, document, "fullscreenElement", "requestFullscreen", "exitFullscreen", "fullscreenchange")
			}
			canvas.mozRequestFullScreen != null    -> {
				RealDelegate(canvas, document, "mozFullscreenElement", "mozRequestFullScreen", "mozCancelFullScreen", "mozwebkitfullscreenchange")
			}
			canvas.webkitRequestFullscreen != null -> {
				RealDelegate(canvas, document, "webkitFullscreenElement", "webkitRequestFullscreen", "webkitExitFullscreen", "webkitfullscreenchange")
			}
			canvas.msRequestFullscreen != null     -> {
				RealDelegate(canvas, document, "msFullscreenElement", "msRequestFullscreen", "msExitFullscreen", "msrequestfullscreenchange")
			}
			else                                   -> {
				FakeDelegate()
			}
		}
		
	}
	
	private fun handleFullscreenChange() {
		introduceChange(delegate.enabled)
	}
	
	private fun makeEnabled(v: Boolean) {
		if (v) delegate.open()
		else delegate.close()
	}
	
	private interface Delegate {
		val enabled: Boolean
		
		fun open()
		
		fun close()
	}
	
	private class FakeDelegate : Delegate {
		override val enabled = false
		
		override fun open() {}
		
		override fun close() {}
	}
	
	private inner class RealDelegate(
		private val canvas: dynamic,
		private val document: dynamic,
		private val element: String,
		private val open: String,
		private val close: String,
		event: String
	) : Delegate {
		
		override val enabled: Boolean
			get() = document[element] != null
		
		init {
			document.addEventListener(event) { handleFullscreenChange() }
		}
		
		override fun open() {
			canvas[open]()
		}
		
		override fun close() {
			document[close]()
		}
	}
}
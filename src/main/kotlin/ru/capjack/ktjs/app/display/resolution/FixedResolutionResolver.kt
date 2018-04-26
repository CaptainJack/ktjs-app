package ru.capjack.ktjs.app.display.resolution

class FixedResolutionResolver(
	private val bitmapImageResolution: Int

) : ResolutionResolver {
	override fun resolveRendererResolution(devicePixelRatio: Double): Double {
		return devicePixelRatio
	}
	
	override fun resolveBitmapImageResolution(rendererResolution: Double): Int {
		return bitmapImageResolution
	}
}
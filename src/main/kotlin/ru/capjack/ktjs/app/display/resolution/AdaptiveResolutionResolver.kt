package ru.capjack.ktjs.app.display.resolution

class AdaptiveResolutionResolver(
	private val maxBitmapImageResolution: Int
) : ResolutionResolver {

	override fun resolveRendererResolution(devicePixelRatio: Double): Double {
		return devicePixelRatio
	}
	
	override fun resolveBitmapImageResolution(rendererResolution: Double): Int {
		return maxBitmapImageResolution.downTo(1).first { it <= rendererResolution }
	}
}
package ru.capjack.ktjs.app.display.resolution

class FixedResolutionResolver(
	private val value: Int
) : ResolutionResolver {
	
	override fun resolveRendererResolution(devicePixelRatio: Double): Double {
		return value.toDouble()
	}
	
	override fun resolveBitmapImageResolution(rendererResolution: Double): Int {
		return value
	}
}
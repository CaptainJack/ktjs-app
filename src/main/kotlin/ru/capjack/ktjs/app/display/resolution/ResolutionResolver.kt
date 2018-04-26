package ru.capjack.ktjs.app.display.resolution

interface ResolutionResolver {
	fun resolveRendererResolution(devicePixelRatio: Double): Double
	
	fun resolveBitmapImageResolution(rendererResolution: Double): Int
}
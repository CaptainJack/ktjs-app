package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.resolution.ResolutionResolver
import ru.capjack.ktjs.common.Confines
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.replaceIfNotFinite
import kotlin.browser.window

internal class DisplaySystemTraitsImpl(
	resolutionResolver: ResolutionResolver,
	
	override val stageSizeConfines: Confines<AxialValues<Int>>,
	
	override val stageSize: AxialValues<Int>

) : DisplaySystemTraits {
	
	override val pixelRatio: Double = window.devicePixelRatio.replaceIfNotFinite(1.0)
	
	override val rendererResolution: Double = resolutionResolver.resolveRendererResolution(pixelRatio)
	
	override val imageResolution: Int = resolutionResolver.resolveBitmapImageResolution(rendererResolution)
	
	override var stageScale: Double = 1.0
}
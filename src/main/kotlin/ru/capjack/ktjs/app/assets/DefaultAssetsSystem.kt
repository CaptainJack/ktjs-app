package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.assets.font.FontFaceRegistry
import ru.capjack.ktjs.app.display.DisplayRenderer
import ru.capjack.ktjs.common.rl.Url

class DefaultAssetsSystem(
	private val fontsBaseUrl: Url,
	private val renderer: DisplayRenderer,
	val settings: AssetsSettings
) : AssetsSystem {
	private val fonts: FontFaceRegistry = mutableMapOf()
	
	override fun createProducer(baseUrl: Url): AssetsProducer {
		return AssetsProducerImpl(renderer, settings, baseUrl, fontsBaseUrl, fonts)
	}
}
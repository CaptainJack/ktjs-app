package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.display.DisplayRenderer
import ru.capjack.ktjs.common.rl.Url
import ru.capjack.ktjs.common.rl.Urls

class DefaultAssetsSystem(
    private val fontsBaseUrl: Url,
    private val renderer: DisplayRenderer,
    override val settings: AssetsSettings
) : AssetsSystem {
    private val fonts: FontFaceRegistry = mutableMapOf()

    override fun createProducer(baseUrl: String, settings: AssetsSettings): AssetsProducer {
        return createProducer(Urls.get(baseUrl), settings)
    }

    override fun createProducer(baseUrl: Url, settings: AssetsSettings): AssetsProducer {
        return AssetsProducerImpl(renderer, settings, baseUrl, fontsBaseUrl, fonts)
    }
}
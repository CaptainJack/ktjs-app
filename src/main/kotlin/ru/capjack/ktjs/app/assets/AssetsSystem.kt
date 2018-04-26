package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.common.rl.Url
import ru.capjack.ktjs.common.rl.Urls

interface AssetsSystem {
	val settings: AssetsSettings
	
	fun createProducer(baseUrl: String, settings: AssetsSettings = this.settings): AssetsProducer
	
	fun createProducer(baseUrl: Url = Urls.EMPTY, settings: AssetsSettings = this.settings): AssetsProducer
}


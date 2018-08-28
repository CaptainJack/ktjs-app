package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.common.rl.Url

interface AssetsSystem {
	fun createProducer(baseUrl: Url): AssetsProducer
	
	fun createProducer(baseUrl: Url, settings: AssetsSettings): AssetsProducer
}


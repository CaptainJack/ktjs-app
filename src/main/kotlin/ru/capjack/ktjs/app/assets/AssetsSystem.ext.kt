package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.common.rl.Urls

fun AssetsSystem.createProducer(baseUrl: String): AssetsProducer {
	return createProducer(Urls.get(baseUrl))
}

fun AssetsSystem.createProducer(baseUrl: String, settings: AssetsSettings): AssetsProducer {
	return createProducer(Urls.get(baseUrl), settings)
}
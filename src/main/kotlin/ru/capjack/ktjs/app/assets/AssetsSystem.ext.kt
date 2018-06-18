package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.common.rl.Urls

fun AssetsSystem.createProducer(baseUrl: String): AssetsProducer {
	return createProducer(Urls.get(baseUrl))
}
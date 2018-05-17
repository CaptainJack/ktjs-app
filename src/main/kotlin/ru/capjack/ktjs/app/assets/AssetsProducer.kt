package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.common.progress.Progress

interface AssetsProducer : AssetsCollector {
	fun load(): Progress
	
	fun load(receiver: (Assets) -> Unit): Progress
}


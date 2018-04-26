package ru.capjack.ktjs.app.sound

import ru.capjack.ktjs.common.Destroyable

interface Sound : Destroyable {
	fun play(): SoundFlow
	
	fun play(settings: SoundFlowSettings): SoundFlow
	
	fun getDuration(): Double
}


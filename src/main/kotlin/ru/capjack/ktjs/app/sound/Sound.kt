package ru.capjack.ktjs.app.sound

interface Sound {
	fun play(): SoundFlow
	
	fun play(settings: SoundFlowSettings): SoundFlow
	
	fun getDuration(): Double
}


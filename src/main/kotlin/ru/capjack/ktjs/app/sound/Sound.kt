package ru.capjack.ktjs.app.sound

interface Sound {
	fun play(system: SoundSystem, volume: Double): SoundFlow
	
	fun play(system: SoundSystem, settings: SoundFlowSettings): SoundFlow
	
	fun getDuration(): Double
}


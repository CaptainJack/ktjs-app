package ru.capjack.ktjs.app.sound

interface SoundSystem {
	var volume: Double
	
	fun play(sound: Sound): SoundFlow
	
	fun play(sound: Sound, settings: SoundFlowSettings): SoundFlow
	
	fun play(sound: Sound, settings: SoundFlowSettings.() -> Unit): SoundFlow
}
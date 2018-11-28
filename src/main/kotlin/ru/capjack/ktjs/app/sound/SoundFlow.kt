package ru.capjack.ktjs.app.sound

interface SoundFlow {
	var volume: Double
	
	fun stop()
	
	fun onComplete(handler: (SoundFlow) -> Unit)
}
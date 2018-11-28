package ru.capjack.ktjs.app.sound

import ru.capjack.ktjs.common.Changeable

interface SoundSystem : Changeable<Double> {
	val muted: Boolean
	
	var volume: Double
	
	fun toggleMute()
	
	fun play(sound: Sound, volume: Double = 1.0): SoundFlow
	
	fun play(sound: Sound, settings: SoundFlowSettings): SoundFlow
}
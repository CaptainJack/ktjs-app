package ru.capjack.ktjs.app.sound

import ru.capjack.ktjs.common.Changeable

interface SoundSystem : Changeable<Double> {
	var volume: Double
	
	val muted: Boolean
	
	fun toggleMute()
	
	fun play(sound: Sound, volume: Double = 1.0): SoundFlow
	
	fun play(sound: Sound, settings: SoundFlowSettings): SoundFlow
	
	fun play(sound: Sound, settings: SoundFlowSettings.() -> Unit): SoundFlow
}
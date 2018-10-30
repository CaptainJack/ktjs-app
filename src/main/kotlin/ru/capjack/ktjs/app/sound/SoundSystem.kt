package ru.capjack.ktjs.app.sound

import ru.capjack.ktjs.common.Changeable

interface SoundSystem : Changeable<Double> {
	var volume: Double
	
	fun play(sound: Sound): SoundFlow
	
	fun play(sound: Sound, settings: SoundFlowSettings): SoundFlow
	
	fun play(sound: Sound, settings: SoundFlowSettings.() -> Unit): SoundFlow
}
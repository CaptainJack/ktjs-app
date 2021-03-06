package ru.capjack.ktjs.app.sound.howler

import ru.capjack.ktjs.app.sound.Sound
import ru.capjack.ktjs.app.sound.SoundFlow
import ru.capjack.ktjs.app.sound.SoundFlowSettings
import ru.capjack.ktjs.app.sound.SoundSystem
import ru.capjack.ktjs.common.Destroyable
import ru.capjack.ktjs.wrapper.howler.Howl

class HowlerSound(private val source: Howl) : Sound, Destroyable {
	override fun getDurationMilliseconds(): Int {
		return (source.duration() * 1000).toInt()
	}
	
	override fun play(system: SoundSystem, volume: Double): SoundFlow {
		return HowlerSoundFlow(system, source, volume)
	}
	
	override fun play(system: SoundSystem, settings: SoundFlowSettings): SoundFlow {
		return HowlerSoundFlow(system, source, settings)
	}
	
	override fun getDuration(): Double {
		return source.duration()
	}
	
	override fun destroy() {
		source.unload()
	}
}

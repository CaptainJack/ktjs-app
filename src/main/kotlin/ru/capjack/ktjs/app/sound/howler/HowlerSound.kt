package ru.capjack.ktjs.app.sound.howler

import ru.capjack.ktjs.app.sound.Sound
import ru.capjack.ktjs.app.sound.SoundFlow
import ru.capjack.ktjs.app.sound.SoundFlowSettings
import ru.capjack.ktjs.wrapper.howler.Howl

class HowlerSound(private val source: Howl) : Sound {
	override fun play(): SoundFlow {
		return HowlerSoundFlow(source)
	}
	
	override fun play(settings: SoundFlowSettings): SoundFlow {
		return HowlerSoundFlow(source, settings)
	}
	
	override fun destroy() {
		source.unload()
	}
	
	override fun getDuration(): Double {
		return source.duration()
	}
}

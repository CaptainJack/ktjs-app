package ru.capjack.ktjs.app.sound.howler

import ru.capjack.ktjs.app.sound.Sound
import ru.capjack.ktjs.app.sound.SoundFlow
import ru.capjack.ktjs.app.sound.SoundFlowSettings
import ru.capjack.ktjs.common.Destroyable
import ru.capjack.ktjs.wrapper.howler.Howl

class HowlerSound(private val source: Howl) : Sound, Destroyable {
	override fun play(): SoundFlow {
		return HowlerSoundFlow(source)
	}
	
	override fun play(settings: SoundFlowSettings): SoundFlow {
		return HowlerSoundFlow(source, settings)
	}
	
	override fun getDuration(): Double {
		return source.duration()
	}
	
	override fun destroy() {
		source.unload()
	}
}

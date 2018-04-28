package ru.capjack.ktjs.app.sound

import ru.capjack.ktjs.common.observable

class DefaultSoundSystem : SoundSystem {
	override var volume: Double by observable(1.0, ::makeVolume)
	
	private val flows: MutableSet<SoundFlow> = mutableSetOf()
	
	override fun play(sound: Sound): SoundFlow {
		return addFlow(sound.play())
	}
	
	override fun play(sound: Sound, settings: SoundFlowSettings): SoundFlow {
		return addFlow(sound.play(settings))
	}
	
	override fun play(sound: Sound, settings: SoundFlowSettings.() -> Unit): SoundFlow {
		return play(sound, SoundFlowSettings().apply(settings))
	}
	
	private fun handleFlowComplete(event: SoundFlow.Event) {
		removeFlow(event.flow)
	}
	
	private fun makeVolume(volume: Double) {
		flows.forEach { it.volume = volume }
	}
	
	private fun addFlow(flow: SoundFlow): SoundFlow {
		flow.onEvent(SoundFlow.Event.Complete::class, ::handleFlowComplete)
		flows.add(flow)
		return flow
	}
	
	private fun removeFlow(flow: SoundFlow) {
		flows.remove(flow)
	}
}
package ru.capjack.ktjs.app.sound

import ru.capjack.ktjs.common.ChangeableObject
import ru.capjack.ktjs.common.Delegates.observable

class DefaultSoundSystem : SoundSystem, ChangeableObject<Double>() {
	override var volume by observable(1.0, ::introduceChange)
	override val muted get() = volume == 0.0
	
	private var beforeMuteVolume = 0.0
	private val flows: MutableSet<SoundFlow> = mutableSetOf()
	
	override fun toggleMute() {
		if (muted) {
			volume = beforeMuteVolume
		}
		else {
			beforeMuteVolume = volume
			volume = 0.0
		}
	}
	
	override fun play(sound: Sound, volume: Double): SoundFlow {
		return addFlow(sound.play(this, volume))
	}
	
	override fun play(sound: Sound, settings: SoundFlowSettings): SoundFlow {
		return addFlow(sound.play(this, settings))
	}
	
	override fun play(sound: Sound, settings: SoundFlowSettings.() -> Unit): SoundFlow {
		return play(sound, SoundFlowSettings().apply(settings))
	}
	
	private fun addFlow(flow: SoundFlow): SoundFlow {
		flow.onEvent(SoundFlow.Event.Complete::class, ::handleFlowComplete)
		flows.add(flow)
		return flow
	}
	
	private fun removeFlow(flow: SoundFlow) {
		flows.remove(flow)
	}
	
	private fun handleFlowComplete(event: SoundFlow.Event) {
		removeFlow(event.flow)
	}
}
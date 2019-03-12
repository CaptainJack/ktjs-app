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
			if (beforeMuteVolume == 0.0) {
				beforeMuteVolume = 1.0
			}
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
	
	private fun addFlow(flow: SoundFlow): SoundFlow {
		flows.add(flow)
		flow.onComplete(::removeFlow)
		return flow
	}
	
	private fun removeFlow(flow: SoundFlow) {
		flow.stop()
		flows.remove(flow)
	}
	
	override fun stop(flow: SoundFlow) {
		removeFlow(flow)
	}
}
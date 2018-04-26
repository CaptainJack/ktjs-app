package ru.capjack.ktjs.app.sound.howler

import ru.capjack.ktjs.app.sound.DefaultSoundFlowSettings
import ru.capjack.ktjs.app.sound.SoundFlow
import ru.capjack.ktjs.app.sound.SoundFlowSettings
import ru.capjack.ktjs.common.Cancelable
import ru.capjack.ktjs.common.events.EventDispatcherImpl
import ru.capjack.ktjs.common.time.GlobalTimeSystem
import ru.capjack.ktjs.wrapper.howler.Events
import ru.capjack.ktjs.wrapper.howler.Howl

class HowlerSoundFlow(
	private val source: Howl,
	private val settings: SoundFlowSettings = DefaultSoundFlowSettings
) : EventDispatcherImpl<SoundFlow.Event>(), SoundFlow {
	
	override var volume: Double = 1.0
		set(value) {
			if (field != value) {
				field = value
				source.volume(volume, id)
			}
		}
	
	private val id = source.play() ?: throw IllegalStateException("Can't play sound")
	private var completed: Boolean = false
	
	private var endTask: Cancelable? = null
	
	init {
		source.on(Events.SEEK, { console.log("on seek", source.seek(id)) }, id)
		
		waitEnd()
		
		if (settings.start != 0.0) {
			seek(settings.start)
		}
		
		if (settings.loop) {
			source.loop(true, id)
		}
	}
	
	private fun waitEnd() {
		if (settings.end > 0.0) {
			endTask = GlobalTimeSystem.schedule((settings.end - settings.start).toInt()) { handleEnd() }
		}
		else {
			source.once(Events.END, ::handleEnd, id)
		}
	}
	
	private fun seek(milliseconds: Double) {
		source.seek(milliseconds / 1000, id)
	}
	
	override fun stop() {
		source.stop(id)
		complete()
	}
	
	private fun handleEnd() {
		endTask?.cancel()
		
		if (settings.loop) {
			if (settings.restart != 0.0) {
				seek(settings.restart)
			}
		}
		
		stop()
		complete()
	}
	
	private fun complete() {
		completed = true
		introduceEvent(SoundFlow.Event.Complete(this))
		clearEventReceivers()
	}
}

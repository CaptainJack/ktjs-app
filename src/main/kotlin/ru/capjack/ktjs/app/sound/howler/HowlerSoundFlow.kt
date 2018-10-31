package ru.capjack.ktjs.app.sound.howler

import ru.capjack.ktjs.app.sound.SoundFlow
import ru.capjack.ktjs.app.sound.SoundFlowSettings
import ru.capjack.ktjs.app.sound.SoundSystem
import ru.capjack.ktjs.common.Cancelable
import ru.capjack.ktjs.common.events.EventDispatcherImpl
import ru.capjack.ktjs.common.time.GlobalTimeSystem
import ru.capjack.ktjs.wrapper.howler.Events
import ru.capjack.ktjs.wrapper.howler.Howl

class HowlerSoundFlow(
	private val system: SoundSystem,
	private val source: Howl,
	private val settings: SoundFlowSettings
) : EventDispatcherImpl<SoundFlow.Event>(), SoundFlow {
	
	override var volume: Double = settings.volume
		set(value) {
			if (field != value) {
				field = value
				applyVolume()
			}
		}
	
	private val absoluteVolume
		get() = system.volume * volume
	
	private val id = source.play() ?: throw IllegalStateException("Can't play sound")
	private var completed: Boolean = false
	private var endTask: Cancelable? = null
	private val systemHandler: Cancelable
	
	init {
		systemHandler = system.onChange(::applyVolume)
		applyVolume()
		waitEnd()
		
		if (settings.start != 0.0) {
			seek(settings.start)
		}
		
		if (settings.loop) {
			source.loop(true, id)
		}
	}
	
	constructor(system: SoundSystem, source: Howl, volume: Double) : this(system, source, SoundFlowSettings.DEFAULT) {
		this.volume = volume
	}
	
	private fun applyVolume() {
		source.volume(absoluteVolume, id)
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
		systemHandler.cancel()
		introduceEvent(SoundFlow.Event.Complete(this))
		clearEventReceivers()
	}
}

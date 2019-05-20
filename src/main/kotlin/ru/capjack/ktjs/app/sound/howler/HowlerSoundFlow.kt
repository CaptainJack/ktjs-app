package ru.capjack.ktjs.app.sound.howler

import ru.capjack.ktjs.app.sound.SoundFlow
import ru.capjack.ktjs.app.sound.SoundFlowSettings
import ru.capjack.ktjs.app.sound.SoundSystem
import ru.capjack.ktjs.common.Cancelable
import ru.capjack.ktjs.common.CancelableDummy
import ru.capjack.ktjs.common.ProcedureGroup
import ru.capjack.ktjs.common.invokeDelayed
import ru.capjack.ktjs.common.time.GlobalTimeSystem
import ru.capjack.ktjs.common.time.schedule
import ru.capjack.ktjs.wrapper.howler.Events
import ru.capjack.ktjs.wrapper.howler.Howl

class HowlerSoundFlow(
	private val system: SoundSystem,
	private val source: Howl,
	private val settings: SoundFlowSettings
) : SoundFlow {
	
	private val id: Int
	private var completed: Boolean = false
	private var endTask: Cancelable? = null
	private var refHandleEnd = ::handleEnd
	private val systemHandler: Cancelable
	private val completeHandlers = ProcedureGroup()
	
	override var volume: Double = settings.volume
		set(value) {
			if (field != value) {
				field = value
				applyVolume()
			}
		}
	
	private val absoluteVolume
		get() = system.volume * volume
	
	init {
		val p = source.play()

		if (p == null) {
			completed = true
			id = 0
			systemHandler = CancelableDummy
		}
		else {
			
			id = p
			systemHandler = system.onChange(::applyVolume)
			
			applyVolume()
			
			if (settings.end > 0.0) {
				waitEnd(settings.start)
			}
			else {
				source.on(Events.END, refHandleEnd, id)
			}
			
			if (settings.start != 0.0) {
				seek(settings.start)
			}
			
			if (settings.loop) {
				source.loop(true, id)
			}
		}
	}
	
	constructor(system: SoundSystem, source: Howl, volume: Double) : this(system, source, SoundFlowSettings.DEFAULT) {
		this.volume = volume
	}
	
	override fun onComplete(handler: (SoundFlow) -> Unit) {
		if (completed) {
			invokeDelayed { handler(this) }
		}
		else {
			completeHandlers.add(handler)
		}
	}
	
	override fun stop() {
		source.stop(id)
		complete()
	}
	
	private fun applyVolume() {
		source.volume(absoluteVolume, id)
	}
	
	private fun waitEnd(offset: Double) {
		endTask?.cancel()
		
		endTask = GlobalTimeSystem.schedule(
			(settings.end - offset).toInt(),
			::handleEnd
		)
	}
	
	private fun seek(milliseconds: Double) {
		source.seek(milliseconds / 1000, id)
	}
	
	private fun handleEnd() {
		if (settings.loop) {
			waitEnd(settings.restart)
			if (settings.restart != 0.0) {
				seek(settings.restart)
			}
		}
		else {
			stop()
		}
	}
	
	private fun complete() {
		if (!completed) {
			completed = true
			source.off(Events.END, refHandleEnd, id)
			endTask?.cancel()
			systemHandler.cancel()
			completeHandlers.clearAndInvoke(this)
		}
	}
}

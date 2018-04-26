package ru.capjack.ktjs.app.sound

import ru.capjack.ktjs.common.events.EventDealer

interface SoundFlow: EventDealer<SoundFlow.Event> {
	sealed class Event(val flow: SoundFlow) {
		class Complete(flow: SoundFlow) : Event(flow)
	}
	
	var volume: Double
	
	fun stop()
}
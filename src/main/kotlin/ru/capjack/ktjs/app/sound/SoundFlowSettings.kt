package ru.capjack.ktjs.app.sound

data class SoundFlowSettings(
	var loop: Boolean = false,
	var start: Double = 0.0,
	var end: Double = 0.0,
	var restart: Double = 0.0,
	var volume: Double = 1.0
) {
	companion object {
		val DEFAULT = SoundFlowSettings()
	}
}
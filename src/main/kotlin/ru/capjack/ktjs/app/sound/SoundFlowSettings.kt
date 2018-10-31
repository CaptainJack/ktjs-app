package ru.capjack.ktjs.app.sound

data class SoundFlowSettings(
	val loop: Boolean = false,
	val start: Double = 0.0,
	val end: Double = 0.0,
	val restart: Double = 0.0,
	val volume: Double = 1.0
) {
	companion object {
		val DEFAULT = SoundFlowSettings()
	}
}
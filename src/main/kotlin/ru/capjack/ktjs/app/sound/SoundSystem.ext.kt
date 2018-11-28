package ru.capjack.ktjs.app.sound

inline fun SoundSystem.play(sound: Sound, settings: SoundFlowSettings.() -> Unit): SoundFlow {
	return play(sound, SoundFlowSettings().apply(settings))
}
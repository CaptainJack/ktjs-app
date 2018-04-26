package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.sound.Sound

internal class SoundAssetImpl : AbstractAsset(), SoundAsset {
	override val sound: Sound
		get() {
			checkAvailable()
			return _sound!!
		}
	
	private var _sound: Sound? = null
	
	fun load(sound: Sound) {
		_sound = sound
		completeLoad()
	}
	
	override fun doDestroy() {
		_sound?.destroy()
	}
}
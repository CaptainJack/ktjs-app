package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.sound.Sound
import ru.capjack.ktjs.app.sound.howler.HowlerSound

internal class SoundAssetImpl : AbstractAsset(), SoundAsset {
	override val sound: Sound
		get() {
			checkAvailable()
			return _sound!!
		}
	
	private var _sound: HowlerSound? = null
	
	fun load(sound: HowlerSound) {
		_sound = sound
		completeLoad()
	}
	
	override fun doDestroy() {
		_sound?.destroy()
	}
}
package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.sound.howler.HowlerSound
import ru.capjack.ktjs.common.js.jso
import ru.capjack.ktjs.common.rl.Url
import ru.capjack.ktjs.wrapper.howler.Howl

internal class SoundAssetMaker(url: Url) : AbstractAssetMaker<SoundAssetImpl>(SoundAssetImpl(), url) {
	private lateinit var howl: Howl
	
	override fun doRun() {
		howl = Howl(jso {
			src = arrayOf(url.value)
			onload = ::complete
		})
	}
	
	override fun doComplete() {
		asset.load(HowlerSound(howl))
		super.doComplete()
	}

}
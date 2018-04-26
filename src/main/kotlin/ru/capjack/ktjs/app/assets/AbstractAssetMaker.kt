package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.common.progress.AbstractProgress
import ru.capjack.ktjs.common.progress.Progress
import ru.capjack.ktjs.common.progress.ProgressRunner
import ru.capjack.ktjs.common.rl.Url

internal abstract class AbstractAssetMaker<out A : AbstractAsset>(
	val asset: A,
	val url: Url
) : AbstractProgress(), ProgressRunner {
	
	final override fun run(): Progress {
		doRun()
		return this
	}
	
	protected abstract fun doRun()
	
	override fun calculatePercent(): Double {
		return 0.0
	}
	
}
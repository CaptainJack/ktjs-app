package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.dom.Container

internal class StageContainer : Container() {
	private lateinit var _stage: Stage
	
	internal fun setStage(stage: Stage) {
		_stage = stage
	}
	
	override fun getState(): Stage {
		return _stage
	}
}

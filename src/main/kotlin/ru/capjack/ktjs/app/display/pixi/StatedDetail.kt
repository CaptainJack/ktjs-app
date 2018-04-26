package ru.capjack.ktjs.app.display.pixi

import ru.capjack.ktjs.app.display.button.ButtonState
import ru.capjack.ktjs.app.display.button.ButtonStateValues

abstract class StatedDetail<T>(protected val stateValues: ButtonStateValues<T>) : ButtonDetail {
	override fun processButtonStateChanged(new: ButtonState, old: ButtonState) {
		setStateValue(stateValues[new])
	}
	
	protected abstract fun setStateValue(value: T)
}
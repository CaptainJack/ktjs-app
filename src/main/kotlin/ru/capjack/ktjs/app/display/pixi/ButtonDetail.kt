package ru.capjack.ktjs.app.display.pixi

import ru.capjack.ktjs.app.display.button.ButtonState
import ru.capjack.ktjs.wrapper.pixi.Displayable

interface ButtonDetail: Displayable {
	fun processButtonStateChanged(new: ButtonState, old: ButtonState)
}


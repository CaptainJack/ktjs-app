package ru.capjack.ktjs.app.display.button

import ru.capjack.ktjs.wrapper.pixi.Displayable

interface ButtonDetail : Displayable {
	fun processButtonStateChanged(new: ButtonState, old: ButtonState)
}


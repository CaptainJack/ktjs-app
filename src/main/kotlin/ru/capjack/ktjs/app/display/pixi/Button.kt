package ru.capjack.ktjs.app.display.pixi

import ru.capjack.ktjs.app.display.button.ButtonControl
import ru.capjack.ktjs.wrapper.pixi.Displayable

interface Button : Displayable {
	val control: ButtonControl
}
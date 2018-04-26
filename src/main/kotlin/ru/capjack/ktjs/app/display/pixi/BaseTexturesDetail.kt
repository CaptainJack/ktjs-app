package ru.capjack.ktjs.app.display.pixi

import ru.capjack.ktjs.app.display.button.ButtonStateValues
import ru.capjack.ktjs.wrapper.pixi.Texture

abstract class BaseTexturesDetail(
	textures: ButtonStateValues<Texture>
) : StatedDetail<Texture>(textures)


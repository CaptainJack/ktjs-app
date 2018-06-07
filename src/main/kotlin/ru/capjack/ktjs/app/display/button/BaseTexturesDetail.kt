package ru.capjack.ktjs.app.display.button

import ru.capjack.ktjs.wrapper.pixi.Texture

abstract class BaseTexturesDetail(
	textures: ButtonStateValues<Texture>
) : StatedDetail<Texture>(textures)


package ru.capjack.ktjs.app.display.pixi

import ru.capjack.ktjs.app.display.misc.Color
import ru.capjack.ktjs.wrapper.pixi.Graphics

fun Graphics.beginFill(color: Color): Graphics {
	beginFill(color.rgb, color.alphaPercent)
	return this
}

fun Graphics.lineStyle(thickness: Number, color: Color): Graphics {
	lineStyle(thickness, color.rgb, color.alphaPercent)
	return this
}
package ru.capjack.ktjs.app.display.misc

import ru.capjack.ktjs.app.assets.font.FontFace
import ru.capjack.ktjs.common.js.jsi
import ru.capjack.ktjs.wrapper.pixi.TextStyle as PixiTextStyle

data class TextStyle(
	val font: FontFace,
	val size: Int,
	val letterSpacing: Double = 0.0,
	val lineHeight: Int = size,
	val color: Color = Colors.BLACK,
	val align: TextAlign = TextAlign.LEFT,
	val transform: (String) -> String = TextStyleTransforms.NOTHING
) {
	val pixi: PixiTextStyle = PixiTextStyle(jsi {
		it.fontFamily = font.family
		it.fontStyle = font.style.value
		it.fontWeight = font.weight.value
		it.fontSize = size
		it.fill = color.css
		it.lineHeight = lineHeight
		it.letterSpacing = letterSpacing
		it.align = align.name.toLowerCase()
	})
}

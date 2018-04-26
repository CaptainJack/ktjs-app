package ru.capjack.ktjs.app.display.misc

import ru.capjack.ktjs.app.assets.font.FontFace
import ru.capjack.ktjs.common.js.jst
import ru.capjack.ktjs.wrapper.pixi.TextStyle as PixiTextStyle

class TextStyle private constructor(
	font: FontFace,
	size: Int,
	letterSpacing: Double,
	lineHeight: Int = size,
	color: Color = Color.BLACK,
	align: TextAlign = TextAlign.LEFT,
	
	var transform: (String) -> String,
	
	val pixi: PixiTextStyle
) {
	constructor(
		font: FontFace,
		size: Int,
		letterSpacing: Double = 0.0,
		lineHeight: Int = size,
		color: Color = Color.BLACK,
		align: TextAlign = TextAlign.LEFT,
		
		transform: (String) -> String = TRANSFORM_NOTHING
	) :
		this(
			font, size, letterSpacing, lineHeight, color, align, transform,
			
			PixiTextStyle(jst {
				this.fontFamily = font.family
				this.fontStyle = font.style.value
				this.fontWeight = font.weight.value
				this.fontSize = size
				this.fill = color.cssValue
				this.lineHeight = lineHeight
				this.letterSpacing = letterSpacing
				this.align = align.name.toLowerCase()
			})
		)
	
	
	companion object {
		val TRANSFORM_NOTHING = { v: String -> v }
		val TRANSFORM_TO_UPPER_CASE = { v: String -> v.toUpperCase() }
		val TRANSFORM_TO_LOWER_CASE = { v: String -> v.toLowerCase() }
	}
	
	var font = font
		set(value) {
			field = value
			pixi.fontFamily = value.family
			pixi.fontStyle = value.style.value
			pixi.fontWeight = value.weight.value
		}
	
	var size = size
		set(value) {
			field = value
			pixi.fontSize = value
		}
	
	var color = color
		set(value) {
			field = value
			pixi.fill = value.cssValue
		}
	
	var lineHeight = lineHeight
		set(value) {
			field = value
			pixi.lineHeight = value
		}
	
	var letterSpacing = letterSpacing
		set(value) {
			field = value
			pixi.letterSpacing = value
		}
	
	var align = align
		set(value) {
			field = value
			pixi.align = align.name.toLowerCase()
		}
	
	inline fun copy(changes: TextStyle.() -> Unit): TextStyle {
		return copy().apply(changes)
	}
	
	fun copy(): TextStyle {
		return TextStyle(font, size, letterSpacing, lineHeight, color, align, transform, pixi.clone())
	}
}

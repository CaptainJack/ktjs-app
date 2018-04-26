package ru.capjack.ktjs.app.display.misc

class Color private constructor(val argb: Int) {
	
	companion object {
		val BLACK = Color(0, alpha = 1.0)
		val GOLD = Color(0xFFFF00, alpha = 1.0)
		val RED = Color(0xFF0000, alpha = 1.0)
		val WHITE = Color(0xFFFFFF, alpha = 1.0)
		
		private fun mergeRgbAndAlpha(rgb: Int, alpha: Double = 1.0): Int {
			if (rgb !in 0..0xFFFFFF) {
				throw IllegalArgumentException("Rgb value must be between 0x000000 and 0xFFFFFF")
			}
			if (alpha !in 0..1) {
				throw IllegalArgumentException("Alpha value must be between 0 and 1")
			}
			
			return rgb or ((alpha * 0xFF).toInt() shl 24)
		}
	}
	
	constructor(rgb: Int, alpha: Double = 1.0) : this(mergeRgbAndAlpha(rgb, alpha))
	
	val alpha: Int
		get() = argb ushr 24
	
	val alphaPercent: Double
		get() = (argb ushr 24) / 0xFF.toDouble()
	
	val red: Int
		get() = (argb ushr 16) and 0xFF
	
	val green: Int
		get() = (argb ushr 8) and 0xFF
	
	val blue: Int
		get() = argb and 0xFF
	
	val rgb: Int
		get() = argb and 0xFFFFFF
	
	val cssValue: String by lazy {
		if (this.alpha == 0xFF) {
			"#" + (rgb.asDynamic().toString(16) as String).padStart(6, '0')
		}
		else {
			"rgba($red, $green, $blue, ${this.alpha})"
		}
	}
	
	fun changeAlpha(value: Double): Color {
		return Color(mergeRgbAndAlpha(rgb, value))
	}
}
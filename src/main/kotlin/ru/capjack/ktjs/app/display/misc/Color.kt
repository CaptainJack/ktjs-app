package ru.capjack.ktjs.app.display.misc

class Color private constructor(val argb: Int) {
	
	constructor(rgb: Int, alphaStrength: Double = 1.0) : this(rgb or ((alphaStrength * 0xFF).toInt() shl 24))
	
	val alpha: Int get() = segment(3)
	val red: Int get() = segment(2)
	val green: Int get() = segment(1)
	val blue: Int get() = segment(0)
	
	val alphaStrength: Double get() = strength(3)
	val redStrength: Double get() = strength(2)
	val greenStrength: Double get() = strength(1)
	val blueStrength: Double get() = strength(0)
	
	val rgb: Int get() = argb and 0xFFFFFF
	
	val css: String by lazy {
		if (alpha == 0xFF) {
			"#" + (rgb.asDynamic().toString(16) as String).padStart(6, '0')
		} else {
			"rgba($red, $green, $blue, $alphaStrength)"
		}
	}
	
	private fun segment(index: Int): Int {
		return argb.ushr(index * 8).and(0xFF)
	}
	
	private fun strength(index: Int): Double {
		return segment(index) / 0xFF.toDouble()
	}
}

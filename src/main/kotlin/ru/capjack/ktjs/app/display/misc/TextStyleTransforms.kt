package ru.capjack.ktjs.app.display.misc

object TextStyleTransforms {
	val NOTHING = { v: String -> v }
	val TO_UPPER_CASE = { v: String -> v.toUpperCase() }
	val TO_LOWER_CASE = { v: String -> v.toLowerCase() }
}
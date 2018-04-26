package ru.capjack.ktjs.app.assets.font

data class FontFace(
	val family: String,
	val weight: Weight = Weight.NORMAL,
	val style: Style = Style.NORMAL
) {
	
	enum class Weight(var value: String) {
		NORMAL("normal"),
		BOLD("bold")
	}
	
	enum class Style(var value: String) {
		NORMAL("normal"),
		ITALIC("italic"),
		OBLIQUE("oblique")
	}
}
package ru.capjack.ktjs.app.display.dom.traits

interface DimensionRule {
	fun isApplicable(type: SpaceType): Boolean
}

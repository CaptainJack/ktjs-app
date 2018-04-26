package ru.capjack.ktjs.app.display

internal interface DisplaySystemTraitsPanelInternal : DisplaySystemTraitsPanel {
	fun processBeginRender()
	
	fun processEndRender()
	
	fun updateSizes()
}
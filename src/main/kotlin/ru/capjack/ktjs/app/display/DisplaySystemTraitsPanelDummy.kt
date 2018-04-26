package ru.capjack.ktjs.app.display

import org.w3c.dom.HTMLElement

internal class DisplaySystemTraitsPanelDummy : DisplaySystemTraitsPanelInternal {
	override val view: HTMLElement
		get() = throw UnsupportedOperationException()
	
	override fun processBeginRender() {}
	override fun processEndRender() {}
	override fun updateSizes() {}
}
package ru.capjack.ktjs.app.display

import org.w3c.dom.HTMLCanvasElement
import ru.capjack.ktjs.app.display.nodes.Box
import ru.capjack.ktjs.app.display.nodes.node.SizeRules
import kotlin.browser.document

class Stage : Box() {
	val canvas: HTMLCanvasElement = document.createElement("canvas") as HTMLCanvasElement
	
	override val stage: Stage = this
	
	init {
		sizeRule = SizeRules.NOTHING
	}
}
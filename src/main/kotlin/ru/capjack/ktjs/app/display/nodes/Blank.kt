package ru.capjack.ktjs.app.display.nodes

import ru.capjack.ktjs.wrapper.pixi.DisplayObject

class Blank : Node() {
	override val view: DisplayObject = DisplayObject()
	
	override fun processSizeChanged() {
	}
}
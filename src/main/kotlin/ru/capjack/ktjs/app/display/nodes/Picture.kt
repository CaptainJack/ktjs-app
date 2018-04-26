package ru.capjack.ktjs.app.display.nodes

import ru.capjack.ktjs.wrapper.pixi.Container

class Picture : NodeOfContainer<Container>(Container()) {
	override fun processSizeChanged() {
	}
}
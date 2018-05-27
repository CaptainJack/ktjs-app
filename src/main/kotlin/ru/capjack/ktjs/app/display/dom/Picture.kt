package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.wrapper.pixi.Container

class Picture(
	override val display: Container = Container(),
	deformable: Boolean = false

) : Tail(deformable) {
	
	init {
		assignContentSizeByDisplay()
	}
	
	fun refreshContentSize() {
		assignContentSizeByDisplay()
	}
}



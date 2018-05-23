package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.wrapper.pixi.Container
import ru.capjack.ktjs.wrapper.pixi.Graphics

abstract class NodeOfContainer : Node() {
	abstract override val display: Container
	
	fun showDebugBackground(color: Int = 0xFF0000) {
		val g = Graphics()
		display.addChildAt(g, 0)
		
		fun draw() {
			g.clear()
				.beginFill(color, 0.2)
				.drawRect(0, 0, size.x, size.y)
				.endFill()
		}
		
		size.onChange(::draw)
		draw()
	}
}
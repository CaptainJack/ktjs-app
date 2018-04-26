package ru.capjack.ktjs.app.display.nodes

import ru.capjack.ktjs.wrapper.pixi.Container
import ru.capjack.ktjs.wrapper.pixi.Graphics
import ru.capjack.ktjs.wrapper.pixi.setSize

abstract class NodeOfContainer<out V : Container>(
	final override val view: V
) : Node() {
	
	private var callAssignViewSizeOnSizeChanged: Boolean = true
	
	init {
		specifySizeByView()
	}
	
	fun specifySizeByView() {
		callAssignViewSizeOnSizeChanged = false
		size.set(view.width.toInt(), view.height.toInt())
		callAssignViewSizeOnSizeChanged = true
	}
	
	override fun processSizeChanged() {
		if (callAssignViewSizeOnSizeChanged) {
			assignViewSize()
		}
	}
	
	fun showDebugBackground(color: Int = 0xFF0000) {
		val g = Graphics()
		view.addChildAt(g, 0)
		
		fun draw() {
			g.clear()
				.beginFill(color, 0.2)
				.drawRect(0, 0, size.horizontal, size.vertical)
				.endFill()
		}
		
		size.addChangeHandler(::draw)
		draw()
	}
	
	protected open fun assignViewSize() {
		view.setSize(size)
	}
	
}


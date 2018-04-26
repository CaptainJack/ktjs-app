package ru.capjack.ktjs.app.display.nodes.box

import ru.capjack.ktjs.app.display.nodes.Box
import ru.capjack.ktjs.app.display.nodes.Graphics
import ru.capjack.ktjs.app.display.nodes.node.PositionRules
import ru.capjack.ktjs.app.display.nodes.node.SizeRules
import ru.capjack.ktjs.common.geom.AxialValuesImpl
import ru.capjack.ktjs.common.math.Random

class GridTestPlate {
	val mainBox: Box
	
	init {
		val grid = GridLayout(
			AxialValuesImpl(100, 100),
			AxialValuesImpl(10, 10),
			false,
			PositionRules.CENTER
		)
		mainBox = Box(grid).apply {
			sizeRule = SizeRules.NOTHING
			size.set(500, 400)
			showDebugBackground(0xff00ff)
			addChildren(createRndNodes())
		}
	}
	
	
	private fun createRndNodes(): MutableList<Graphics> {
		val nodes: MutableList<Graphics> = mutableListOf()
		for (i: Int in 0..3) {
			val rndW = Random.make(100)
			val rndH = Random.make(100)
//			console.log("size = " + rndW + "  " + rndH)
			val node = Graphics().apply {
				beginFill(Random.make(0xffffff), 0.5)
				drawRect(0, 0, width = rndW, height = rndH)
//				drawRect(0, 0, width = 100, height = 100)
				endFill()
			}
			nodes.add(i, node)
		}
		return nodes
	}
}
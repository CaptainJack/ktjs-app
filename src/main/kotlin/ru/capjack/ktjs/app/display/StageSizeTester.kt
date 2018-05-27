package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.dom.Graphics

class StageSizeTester(private val stage: Stage) : Graphics() {
	init {
		stage.size.onChange(::update)
		update()
	}
	
	private fun update() {
		val corner = 50
		
		val outerColor = 0x666666
		val outerCornerColor = 0x996666
		val innerColor = 0x333333
		val innerCornerColor = 0x339933
		
		val outer = stage.size
		val inner = stage.sizeConfines.min
		
		val x = (outer.x - inner.x) / 2
		val y = (outer.y - inner.y) / 2
		
		clearAndDraw {
			fillRect(outerColor, 0, 0, outer.x, outer.y)
			fillRect(innerColor, x, y, inner.x, inner.y)
			
			fillRect(outerCornerColor, 0, 0, corner, corner)
			fillRect(outerCornerColor, outer.x - corner, 0, corner, corner)
			fillRect(outerCornerColor, outer.x - corner, outer.y - corner, corner, corner)
			fillRect(outerCornerColor, 0, outer.y - corner, corner, corner)
			
			fillRect(innerCornerColor, x, y + 0, corner, corner)
			fillRect(innerCornerColor, x + inner.x - corner, y + 0, corner, corner)
			fillRect(innerCornerColor, x + inner.x - corner, y + inner.y - corner, corner, corner)
			fillRect(innerCornerColor, x, y + inner.y - corner, corner, corner)
		}
		
	}
}
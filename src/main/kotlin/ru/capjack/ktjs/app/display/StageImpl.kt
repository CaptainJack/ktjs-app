package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.dom.Box
import ru.capjack.ktjs.app.display.dom.NodeList
import ru.capjack.ktjs.common.Confines
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.ChangeableAxialValues
import ru.capjack.ktjs.wrapper.pixi.DisplayObject

internal class StageImpl private constructor(
	override val sizeConfines: Confines<AxialValues<Int>>,
	private val box: Box
) : Stage, NodeList by box {
	
	val display: DisplayObject get() = box.display
	
	override val size: ChangeableAxialValues<Int> get() = box.size
	override var scale: Double = 1.0
		private set
	
	init {
		box.showDebugBackground()
	}
	
	constructor(sizeConfines: Confines<AxialValues<Int>>) : this(sizeConfines, Box())
	
	fun locate(position: AxialValues<Int>, size: AxialValues<Int>, scale: Double) {
		this.scale = scale
		box.position.set(position)
		box.size.set(size)
	}
}
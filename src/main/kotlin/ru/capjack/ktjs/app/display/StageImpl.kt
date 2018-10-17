package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.dom.NodeList
import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.ChangeableAxial
import ru.capjack.ktjs.wrapper.pixi.DisplayObject
import ru.capjack.ktjs.wrapper.pixi.set

internal class StageImpl private constructor(
	override val displaySystem: DisplaySystem,
	private val container: StageContainer
) : Stage, NodeList by container {
	
	val display: DisplayObject
		get() = container.display
	
	override val size: ChangeableAxial<Int>
		get() = container.size
	
	init {
		container.setStage(this)
	}
	
	constructor(displaySystem: DisplaySystem) : this(displaySystem, StageContainer())
	
	fun locate(position: Axial<Int>, size: Axial<Int>, scale: Double) {
		container.display.scale.set(scale)
		container.position.set(position)
		container.size.set(size)
	}
}
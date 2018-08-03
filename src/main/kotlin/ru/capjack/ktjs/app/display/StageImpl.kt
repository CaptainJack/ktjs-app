package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.dom.Container
import ru.capjack.ktjs.app.display.dom.NodeList
import ru.capjack.ktjs.common.Confines
import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.ChangeableAxial
import ru.capjack.ktjs.wrapper.pixi.DisplayObject
import ru.capjack.ktjs.wrapper.pixi.set

internal class StageImpl private constructor(
	override val sizeConfines: Confines<Axial<Int>>,
	private val container: Container
) : Stage, NodeList by container {
	
	val display: DisplayObject
		get() = container.display
	
	override val size: ChangeableAxial<Int>
		get() = container.size
	
	override val scale: Double
		get() = container.display.scale.x
	
	constructor(sizeConfines: Confines<Axial<Int>>) : this(sizeConfines, Container())
	
	fun locate(position: Axial<Int>, size: Axial<Int>, scale: Double) {
		container.display.scale.set(scale)
		container.position.set(position)
		container.size.set(size)
	}
	
	override fun showSizeTester() {
		container.addNode(StageSizeTester(this))
	}
}
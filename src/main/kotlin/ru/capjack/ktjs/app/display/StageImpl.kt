package ru.capjack.ktjs.app.display

import ru.capjack.ktjs.app.display.dom.NodeList
import ru.capjack.ktjs.common.Confines
import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.ChangeableAxial
import ru.capjack.ktjs.wrapper.pixi.DisplayObject
import ru.capjack.ktjs.wrapper.pixi.set

internal class StageImpl private constructor(
	override val sizeConfines: Confines<Axial<Int>>,
	private val box: StageBox
) : Stage, NodeList by box {
	
	val display: DisplayObject
		get() = box.display
	
	override val size: ChangeableAxial<Int>
		get() = box.size
	
	override val scale: Double
		get() = box.display.scale.x
	
	constructor(sizeConfines: Confines<Axial<Int>>) : this(sizeConfines, StageBox())
	
	fun locate(position: Axial<Int>, size: Axial<Int>, scale: Double) {
		box.display.scale.set(scale)
		box.position.set(position)
		box.size.set(size)
	}
	
	override fun showSizeTester() {
		box.addNode(StageSizeTester(this))
	}
}
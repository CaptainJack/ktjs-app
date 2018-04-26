package ru.capjack.ktjs.app.display.nodes

import ru.capjack.ktjs.app.display.Stage
import ru.capjack.ktjs.app.display.nodes.node.PositionRule
import ru.capjack.ktjs.app.display.nodes.node.PositionRules
import ru.capjack.ktjs.app.display.nodes.node.SizeRule
import ru.capjack.ktjs.app.display.nodes.node.SizeRules
import ru.capjack.ktjs.app.display.nodes.node.Space
import ru.capjack.ktjs.app.display.nodes.node.SpaceImpl
import ru.capjack.ktjs.common.Destroyable
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.ifNotNull
import ru.capjack.ktjs.wrapper.pixi.DisplayObject
import ru.capjack.ktjs.wrapper.pixi.set

abstract class Node : Destroyable {
	abstract val view: DisplayObject
	
	val position: Space = SpaceImpl(::adjustPositionValue)
	val size: Space = SpaceImpl(::adjustSizeValue)
	
	var positionRule: PositionRule = PositionRules.NOTHING
	var sizeRule: SizeRule = SizeRules.NOTHING
	
	var parent: Box? = null
		set(value) {
			if (field != value) {
				field.ifNotNull {
					field = null
					it.removeChild(this)
				}
				field = value
				value?.addChild(this)
				assignViewPosition()
			}
		}
	
	open val stage: Stage?
		get() = parent?.stage
	
	init {
		position.addChangeHandler(::processPositionChanged)
		size.addChangeHandler(::processSizeChanged)
	}
	
	override fun destroy() {
		parent = null
		(size as Destroyable).destroy()
		(position as Destroyable).destroy()
		view.destroy()
	}
	
	protected open fun adjustPositionValue(axis: Axis, value: Int): Int {
		return value
	}
	
	protected open fun adjustSizeValue(axis: Axis, value: Int): Int {
		return value.coerceAtLeast(0)
	}
	
	protected open fun processPositionChanged() {
		assignViewPosition()
	}
	
	protected abstract fun processSizeChanged()
	
	private fun assignViewPosition() {
		val p = parent?.padding
		if (p == null) {
			view.position.set(position)
		}
		else {
			view.position.set(position.horizontal + p.left, position.vertical + p.top)
		}
	}
}
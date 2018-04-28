package ru.capjack.ktjs.app.display.nodes

import org.w3c.dom.events.Event
import org.w3c.dom.events.WheelEvent
import ru.capjack.ktjs.app.display.nodes.node.SizeExpansion
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.ifNotNull
import ru.capjack.ktjs.common.js.jso
import ru.capjack.ktjs.wrapper.greensock.TweenLite
import ru.capjack.ktjs.wrapper.pixi.Container
import ru.capjack.ktjs.wrapper.pixi.Graphics
import ru.capjack.ktjs.wrapper.pixi.get
import ru.capjack.ktjs.wrapper.pixi.interaction.InteractionEvent
import ru.capjack.ktjs.wrapper.pixi.set
import kotlin.browser.window
import kotlin.math.abs

class ScrollableContainer(
	private val axis: Axis,
	private val body: Node
) : NodeOfContainer<Container>(Container()) {
	
	var enabled: Boolean = false
		set(value) {
			if (value != field) {
				field = value
				view.interactive = value
				if (value) activate()
				else deactivate()
			}
		}
	
	private val mask = Graphics()
	private val referenceToHandlePointerDown = ::handlePointerDown
	private val referenceToHandlePointerUp = ::handlePointerUp
	private val referenceToHandlePointerMove = ::handlePointerMove
	private val referenceToHandleMouseOver = ::handleMouseOver
	private val referenceToHandleMouseOut = ::handleMouseOut
	private val referenceToHandleMouseWheel = ::handleMouseWheel
	
	private var lastPointerPosition: Double = 0.0
	private var lastPointerOffset: Double = 0.0
	private var lastMoveTime: Double = 0.0
	private var positionEndLimit: Double = 0.0
	private var dragging: Boolean = false
	private var tween: TweenLite? = null
	
	init {
		view.addChild(body.view)
		view.addChild(mask)
		
		body.view.mask = mask
		body.size.onChange(::processBodySizeChange)
		
		updateMask()
		
		enabled = true
	}
	
	override fun destroy() {
		enabled = false
		super.destroy()
	}
	
	override fun processSizeChanged() {
		updatePositionEndLimit()
		updateMask()
		body.sizeRule.apply(body.size, size, SizeExpansion.FILLING)
	}
	
	private fun processBodySizeChange() {
		updatePositionEndLimit()
	}
	
	private fun updatePositionEndLimit() {
		positionEndLimit = (size[axis] - body.size[axis]).coerceAtMost(0).toDouble()
	}
	
	private fun updateMask() {
		mask.apply {
			clear()
			beginFill()
			drawRect(0, 0, size.horizontal, size.vertical)
			endFill()
		}
	}
	
	private fun stopTween() {
		tween.ifNotNull {
			it.kill()
			tween = null
		}
	}
	
	private fun activate() {
		view.once("pointerdown", referenceToHandlePointerDown)
		view.once("mouseover", referenceToHandleMouseOver)
	}
	
	private fun deactivate() {
		view.off("pointerdown", referenceToHandlePointerDown)
		view.off("pointermove", referenceToHandlePointerMove)
		view.off("pointerup", referenceToHandlePointerUp)
		view.off("pointerupoutside", referenceToHandlePointerUp)
		view.off("mouseover", referenceToHandleMouseOver)
		view.off("mouseout", referenceToHandleMouseOut)
		view.off("mouseupoutside", referenceToHandleMouseOut)
		stopTween()
	}
	
	private fun handleMouseOver() {
		view.once("mouseout", referenceToHandleMouseOut)
		view.once("mouseupoutside", referenceToHandleMouseOut)
		stage?.canvas?.addEventListener("mousewheel", referenceToHandleMouseWheel)
	}
	
	private fun handleMouseOut() {
		view.off("mouseout", referenceToHandleMouseOut)
		view.off("mouseupoutside", referenceToHandleMouseOut)
		view.once("mouseover", referenceToHandleMouseOver)
		stage?.canvas?.removeEventListener("mousewheel", referenceToHandleMouseWheel)
	}
	
	private fun handleMouseWheel(e: Event) {
		if (e is WheelEvent && !dragging) {
			val delta = if (axis == Axis.HORIZONTAL) e.deltaY else -e.deltaY
			val offset = when (e.deltaMode) {
				WheelEvent.DOM_DELTA_PIXEL -> delta
				WheelEvent.DOM_DELTA_LINE  -> delta * 16
				WheelEvent.DOM_DELTA_PAGE  -> delta * 32
				else                       -> delta
			}
			move(offset)
		}
	}
	
	private fun updateLastPointerPosition(e: InteractionEvent) {
		lastPointerPosition = e.data.global[axis]
	}
	
	private fun updateLastPointer(e: InteractionEvent) {
		val value = e.data.global[axis]
		lastPointerOffset = value - lastPointerPosition
		lastPointerPosition = value
	}
	
	private fun move(offset: Double) {
		val value = (body.view.position[axis] + offset)
			.coerceIn(positionEndLimit, 0.0)
		body.view.position[axis] = value
	}
	
	private fun handlePointerDown(e: InteractionEvent) {
		if (enabled) {
			dragging = true
			
			stopTween()
			updateLastPointerPosition(e)
			
			view.on("pointermove", referenceToHandlePointerMove)
			view.once("pointerup", referenceToHandlePointerUp)
			view.once("pointerupoutside", referenceToHandlePointerUp)
		}
	}
	
	private fun handlePointerUp() {
		if (enabled) {
			view.off("pointermove", referenceToHandlePointerMove)
			view.off("pointerup", referenceToHandlePointerUp)
			view.off("pointerupoutside", referenceToHandlePointerUp)
			
			if (lastPointerOffset != 0.0) {
				val timeAtMove = window.performance.now() - lastMoveTime
				if (timeAtMove < 100) {
					val currentPosition = body.view.position[axis]
					val newPosition = (currentPosition + lastPointerOffset * 10).coerceIn(positionEndLimit, 0.0)
					var time = abs((newPosition - currentPosition) / lastPointerOffset) * timeAtMove / 500
					
					time = time.coerceIn(0.2, 0.5)
					
					tween = TweenLite(body.view, time, when (axis) {
						Axis.HORIZONTAL -> jso { x = newPosition }
						Axis.VERTICAL   -> jso { y = newPosition }
					})
				}
			}
			view.once("pointerdown", referenceToHandlePointerDown)
			
			dragging = false
		}
	}
	
	private fun handlePointerMove(e: InteractionEvent) {
		lastMoveTime = window.performance.now()
		if (enabled) {
			updateLastPointer(e)
			move(lastPointerOffset)
		}
	}
}
package ru.capjack.ktjs.app.display.pixi

import ru.capjack.ktjs.app.display.button.ButtonControl
import ru.capjack.ktjs.app.display.button.ButtonControlImp
import ru.capjack.ktjs.common.geom.Rectangle
import ru.capjack.ktjs.wrapper.pixi.Container
import ru.capjack.ktjs.wrapper.pixi.DisplayObject
import ru.capjack.ktjs.wrapper.pixi.Graphics
import ru.capjack.ktjs.wrapper.pixi.addChild
import ru.capjack.ktjs.wrapper.pixi.set

open class CompositeButton(
	details: List<ButtonDetail>,
	hitArea: DisplayObject? = null
) : Button {
	
	constructor(vararg details: ButtonDetail) : this(details.toList())
	
	constructor(hitArea: Rectangle<Int>, vararg details: ButtonDetail) : this(
		details.toList(),
		Graphics().apply {
			position.set(hitArea.position)
			beginFill(0, 0.0)
			drawRect(0, 0, hitArea.width, hitArea.height)
			endFill()
		}
	)
	
	final override val display: Container = Container()
	final override val control: ButtonControl
	
	private val details = details.toMutableList()
	
	init {
		details.forEach { display.addChild(it) }
		
		control = ButtonControlImp(
			if (hitArea == null) {
				display
			}
			else {
				display.addChild(hitArea)
				hitArea
			}
		)
		
		control.addStateChangeHandler({ new, old ->
			details.forEach { it.processButtonStateChanged(new, old) }
		})
	}
	
	fun addDetail(detail: ButtonDetail) {
		detail.processButtonStateChanged(control.state, control.state)
		display.addChildAt(detail.display, details.size)
		details.add(detail)
	}
}


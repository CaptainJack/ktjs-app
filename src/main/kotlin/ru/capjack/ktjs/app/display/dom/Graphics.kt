package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.wrapper.pixi.Graphics as PixiGraphics

open class Graphics(
	deformable: Boolean = false
) : Tail(deformable) {
	
	override val display = PixiGraphics()
	
	private var drawCallsCounter: Int = 0
	
	fun clear(): Graphics {
		display.clear()
		return tryUpdateSize()
	}
	
	inline fun draw(code: Graphics.() -> Unit): Graphics {
		beginDraw()
		code()
		return endDraw()
	}
	
	inline fun clearAndDraw(code: Graphics.() -> Unit): Graphics {
		beginDraw()
		clear()
		code()
		return endDraw()
	}
	
	fun beginDraw(): Graphics {
		++drawCallsCounter
		return this
	}
	
	fun endDraw(): Graphics {
		if (--drawCallsCounter <= 0) {
			drawCallsCounter = 0
			updateContentSizeByDisplay()
		}
		return this
	}
	
	fun beginFill(color: Int = 0, alpha: Double = 1.0): Graphics {
		display.beginFill(color, alpha)
		return this
	}
	
	fun endFill(): Graphics {
		display.endFill()
		return this
	}
	
	fun drawCircle(x: Number, y: Number, radius: Number): Graphics {
		display.drawCircle(x, y, radius)
		return tryUpdateSize()
	}
	
	fun drawRect(x: Number = 0, y: Number = 0, width: Number, height: Number): Graphics {
		display.drawRect(x, y, width, height)
		return tryUpdateSize()
	}
	
	fun drawEllipse(x: Number = 0, y: Number = 0, width: Number, height: Number): Graphics {
		display.drawEllipse(x, y, width, height)
		return tryUpdateSize()
	}
	
	fun drawRoundedRect(x: Number = 0, y: Number = 0, width: Number, height: Number, radius: Number): Graphics {
		display.drawRoundedRect(x, y, width, height, radius)
		return tryUpdateSize()
	}
	
	fun fillRect(color: Int, x: Number = 0, y: Number = 0, width: Number, height: Number): Graphics {
		beginFill(color)
		drawRect(x, y, width, height)
		endFill()
		return this
	}
	
	fun fillRoundedRect(color: Int, x: Number = 0, y: Number = 0, width: Number, height: Number, radius: Number): Graphics {
		beginFill(color)
		drawRoundedRect(x, y, width, height, radius)
		endFill()
		return this
	}
	
	fun lineStyle(lineWidth: Int, color: Int, alpha: Double = 1.0): Graphics {
		display.lineStyle(lineWidth, color, alpha)
		return tryUpdateSize()
	}
	
	private fun tryUpdateSize(): Graphics {
		if (drawCallsCounter == 0) {
			updateContentSizeByDisplay()
		}
		return this
	}
	
}
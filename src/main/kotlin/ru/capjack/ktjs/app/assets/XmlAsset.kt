package ru.capjack.ktjs.app.assets

import org.w3c.dom.Document
import ru.capjack.ktjs.wrapper.pixi.SvgGraphics

interface XmlAsset : Asset {
	val document: Document
	
	fun createSvgGraphics(): SvgGraphics
}
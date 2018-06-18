package ru.capjack.ktjs.app.assets

import org.w3c.dom.Document
import ru.capjack.ktjs.wrapper.pixi.SvgGraphics

internal class XmlAssetImpl : AbstractAsset(), XmlAsset {
	override val document: Document
		get() {
			checkAvailable()
			return _document!!
		}
	
	private var _document: Document? = null
	
	override fun createSvgGraphics(): SvgGraphics {
		return SvgGraphics(document.rootElement!!)
	}
	
	fun load(document: Document) {
		_document = document
		completeLoad()
	}
	
	override fun doDestroy() {
		_document?.clear()
		_document
	}
}

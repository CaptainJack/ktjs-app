package ru.capjack.ktjs.app.display

import org.w3c.dom.HTMLElement
import ru.capjack.ktjs.common.toStringFormatted
import kotlin.browser.document

internal class DisplaySystemTraitsPanelImpl(private val traits: DisplaySystemTraits) : DisplaySystemTraitsPanelInternal {
	override val view: HTMLElement = document.createElement("div") as HTMLElement
	
	private var sizes: HTMLElement
	
	private var elVisible: HTMLElement = document.createElement("div") as HTMLElement
	private var elHidden: HTMLElement = document.createElement("div") as HTMLElement
	
	init {
		view.setAttribute("style", "position:absolute;top:0;left:0;z-index:10000;opacity:0.9;")
		view.appendChild(elVisible)
		view.appendChild(elHidden)
		
		
		elHidden.style.apply {
			display = "none"
			width = "10px"
			height = "10px"
			backgroundColor = "#000"
			opacity = "0.5"
		}
		
		sizes = document.createElement("div") as HTMLElement
		sizes.style.apply {
			backgroundColor = "#000"
			fontSize = "8px"
			fontFamily = "Consolas,\"Lucida Console\",Monaco,monospace"
			color = "#fff"
			padding = "3px 5px"
			display = "inline-block"
			verticalAlign = "top"
			height = "42px"
			whiteSpace = "pre"
		}
		
		sizes.innerHTML = "" +
			"PR <span>-</span><br>" +
			"RR <span>-</span><br>" +
			"IR <span>-</span><br>" +
			"SS <span>-</span>"
		
		
		elVisible.appendChild(sizes)
		
		sizes.onclick = {
			elHidden.style.display = "block"
			elVisible.style.display = "none"
			Unit
		}
		
		elHidden.onclick = {
			elHidden.style.display = "none"
			elVisible.style.display = "block"
			Unit
		}
		
		updateSizes()
	}
	
	override fun processBeginRender() {
	}
	
	override fun processEndRender() {
	}
	
	override fun updateSizes() {
		setValues(
			traits.pixelRatio,
			traits.rendererResolution,
			traits.imageResolution,
			traits.stageScale
		)
	}
	
	private fun setValues(pr: Double, rr: Double, ir: Int, ss: Double) {
		val elements: dynamic = sizes.getElementsByTagName("span")
		elements[0].innerText = pr.toStringFormatted()
		elements[1].innerText = rr.toStringFormatted()
		elements[2].innerText = ir.toString()
		elements[3].innerText = ss.toStringFormatted()
	}
}
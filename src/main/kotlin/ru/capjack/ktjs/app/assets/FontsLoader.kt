package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.assets.font.FontFace
import ru.capjack.ktjs.common.progress.AbstractProgress
import ru.capjack.ktjs.common.progress.Progress
import ru.capjack.ktjs.common.progress.ProgressRunner
import ru.capjack.ktjs.common.rl.Url
import ru.capjack.ktjs.wrapper.webfontloader.WebFont
import ru.capjack.ktjs.wrapper.webfontloader.custom
import ru.capjack.ktjs.wrapper.webfontloader.load
import kotlin.browser.document
import kotlin.dom.appendElement

internal class FontsLoader(
	private val baseUrl: Url,
	private val fonts: MutableList<FontFace>

) : AbstractProgress(), ProgressRunner {
	
	override fun calculatePercent() = 0.0
	
	override fun run(): Progress {
		
		document.head!!.appendElement("style") {
			appendChild(
				document.createTextNode(
					fonts.joinToString("", transform = ::createCssCode)
				)
			)
		}
		
		WebFont.load {
			custom {
				families = fonts.map(FontFace::family).toTypedArray()
			}
			loading = ::complete
		}
		
		return this
	}
	
	private fun createCssCode(font: FontFace): String {
		val url = resolveUrl(font)
		return "@font-face {\n" +
			"font-family: \"${font.family}\";\n" +
			"font-weight: ${font.weight.value};\n" +
			"font-style: ${font.style.value};\n" +
			"src: url(\"$url.woff2\") format(\"woff2\"), url(\"$url.woff\") format(\"woff\");\n" +
			"}\n"
	}
	
	private fun resolveUrl(font: FontFace): Url {
		var file = font.family.replace(' ', '_')
		
		if (font.weight != FontFace.Weight.NORMAL) {
			file += "-${font.weight.value.capitalize()}"
		}
		if (font.style != FontFace.Style.NORMAL) {
			file += "-${font.style.value.capitalize()}"
		}
		
		return baseUrl.resolvePath(file)
	}
}

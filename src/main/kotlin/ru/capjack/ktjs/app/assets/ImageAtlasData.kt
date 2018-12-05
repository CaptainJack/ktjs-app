package ru.capjack.ktjs.app.assets

import org.w3c.dom.XMLDocument
import org.w3c.dom.asList
import org.w3c.dom.get
import ru.capjack.ktjs.common.geom.AxialInstances
import ru.capjack.ktjs.common.geom.axial
import ru.capjack.ktjs.common.geom.rect
import ru.capjack.ktjs.common.geom.rotate

internal class ImageAtlasData(
	val imagePath: String,
	val items: List<ImageAtlasItem>
) {
	enum class SourceType {
		JSON,
		XML
	}
	
	companion object {
		fun parseJson(data: dynamic): ImageAtlasData {
			try {
				return parseJsonCjKtjs(if (data is String) JSON.parse(data) else data)
			}
			catch (e: Throwable) {
				throw IllegalArgumentException("Bad json (${e.message}) : " + JSON.stringify(data))
			}
		}
		
		fun parseXml(data: XMLDocument): ImageAtlasData {
			val items: MutableList<ImageAtlasItem> = mutableListOf()
			
			val atlas = data.getElementsByTagName("TextureAtlas")[0]!!
			var sprites = atlas.getElementsByTagName("sprite")
			
			if (sprites.length != 0) {
				for (sprite in sprites.asList()) {
					val name = sprite.getAttribute("n")!!
					val frame = rect(
						sprite.getAttribute("x")!!.toInt(),
						sprite.getAttribute("y")!!.toInt(),
						sprite.getAttribute("w")!!.toInt(),
						sprite.getAttribute("h")!!.toInt()
					)
					val rotated = sprite.getAttribute("r").equals("y")
					val size = if (rotated) frame.size.rotate() else frame.size
					items.add(ImageAtlasItem(name, frame, size, AxialInstances.INT_0, rotated))
				}
			}
			else {
				sprites = atlas.getElementsByTagName("SubTexture")
				
				for (sprite in sprites.asList()) {
					val name = sprite.getAttribute("name")!!
					val frame = rect(
						sprite.getAttribute("x")!!.toInt(),
						sprite.getAttribute("y")!!.toInt(),
						sprite.getAttribute("width")!!.toInt(),
						sprite.getAttribute("height")!!.toInt()
					)
					val rotated = sprite.getAttribute("r").equals("y")
					
					items.add(
						ImageAtlasItem(name, frame, frame.size, AxialInstances.INT_0, rotated)
					)
				}
			}
			return ImageAtlasData(atlas.getAttribute("imagePath")!!, items)
		}
		
		private fun parseJsonCjKtjs(data: dynamic): ImageAtlasData {
			val items: MutableList<ImageAtlasItem> = mutableListOf()
			val itemsData: Array<dynamic> = data[1] as Array<dynamic>
			
			for (d in itemsData) {
				val l = d.length
				val frame = rect(d[1] as Int, d[2] as Int, d[3] as Int, d[4] as Int)
				val rotated = l == 6 || l == 10
				val trim = when {
					l >= 9 -> axial(d[5] as Int, d[6] as Int)
					else   -> AxialInstances.INT_0
				}
				val size = when {
					l >= 9  -> axial(d[7] as Int, d[8] as Int)
					rotated -> frame.size.rotate()
					else    -> frame.size
				}
				
				items.add(ImageAtlasItem(d[0] as String, frame, size, trim, rotated))
			}
			
			
			return ImageAtlasData(data[0] as String, items)
		}
	}
	
}


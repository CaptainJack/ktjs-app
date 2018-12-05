package ru.capjack.ktjs.app.assets

import org.w3c.dom.XMLDocument
import org.w3c.dom.events.Event
import org.w3c.xhr.DOCUMENT
import org.w3c.xhr.JSON
import org.w3c.xhr.XMLHttpRequest
import org.w3c.xhr.XMLHttpRequestResponseType
import ru.capjack.ktjs.common.rl.Url

internal class ImageAtlasDataLoader(
	private val url: Url,
	private val receiver: (ImageAtlasData) -> Unit
) {
	
	private val request = XMLHttpRequest()
	private val sourceType = defineSourceType(url)
	
	init {
		request.apply {
			responseType = when (sourceType) {
				ImageAtlasData.SourceType.JSON -> XMLHttpRequestResponseType.JSON
				ImageAtlasData.SourceType.XML  -> XMLHttpRequestResponseType.DOCUMENT
			}
			onload = ::processOnLoad
			onerror = ::processOnError
			open("GET", url.value, true)
			send()
		}
	}
	
	private fun defineSourceType(url: Url): ImageAtlasData.SourceType {
		val extension = url.path.name.extension
		
		return when (extension) {
			"json" -> ImageAtlasData.SourceType.JSON
			"xml"  -> ImageAtlasData.SourceType.XML
			else   -> throw RuntimeException("Unsupported ImageAtlas file extension \"$extension\" ")
		}
	}
	
	private fun processOnLoad(event: Event) {
		if (request.readyState == XMLHttpRequest.DONE && request.status == 200.toShort()) {
			releaseRequest()
			
			val data = when (sourceType) {
				ImageAtlasData.SourceType.JSON -> ImageAtlasData.parseJson(request.response)
				ImageAtlasData.SourceType.XML  -> ImageAtlasData.parseXml(request.response as XMLDocument)
			}
			receiver.invoke(data)
		}
		else {
			processOnError(event)
		}
	}
	
	private fun processOnError(event: Event) {
		releaseRequest()
		throw FileLoadFailException(url, event)
	}
	
	private fun releaseRequest() {
		request.onload = null
		request.onerror = null
	}
}
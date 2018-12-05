package ru.capjack.ktjs.app.assets

import org.w3c.dom.events.Event
import org.w3c.xhr.JSON
import org.w3c.xhr.XMLHttpRequest
import org.w3c.xhr.XMLHttpRequestResponseType
import ru.capjack.ktjs.common.rl.Url

internal class JsonAssetMaker(url: Url) : AbstractAssetMaker<JsonAssetImpl>(JsonAssetImpl(), url) {
	
	private val request = XMLHttpRequest()
	
	override fun doRun() {
		request.apply {
			responseType = XMLHttpRequestResponseType.JSON
			onload = ::processOnLoad
			onerror = ::processOnError
			open("GET", url.value, true)
			send()
		}
	}
	
	private fun processOnLoad(event: Event) {
		if (request.readyState == XMLHttpRequest.DONE && request.status == 200.toShort()) {
			releaseRequest()
			
			asset.load(request.response)
			complete()
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
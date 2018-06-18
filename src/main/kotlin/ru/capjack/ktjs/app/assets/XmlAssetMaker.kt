package ru.capjack.ktjs.app.assets

import org.w3c.xhr.XMLHttpRequest
import ru.capjack.ktjs.common.rl.Url

internal class XmlAssetMaker(url: Url) : AbstractAssetMaker<XmlAssetImpl>(XmlAssetImpl(), url) {
	
	override fun doRun() {
		XMLHttpRequest().apply {
			onreadystatechange = {
				if (readyState == XMLHttpRequest.DONE) {
					asset.load(responseXML!!)
					complete()
				}
			}
			open("GET", url.value, true)
			send()
		}
	}
}
package ru.capjack.ktjs.app.assets

import org.w3c.xhr.XMLHttpRequest
import ru.capjack.ktjs.common.rl.Url

internal class JsonAssetMaker(url: Url) : AbstractAssetMaker<JsonAssetImpl>(JsonAssetImpl(), url) {
	
	override fun doRun() {
		XMLHttpRequest().apply {
			onreadystatechange = {
				if (readyState == XMLHttpRequest.DONE) {
					asset.load(JSON.parse(responseText))
					complete()
				}
			}
			open("GET", url.value, true)
			send()
		}
	}
}
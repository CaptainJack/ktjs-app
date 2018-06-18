package ru.capjack.ktjs.app.assets

import org.w3c.dom.HTMLVideoElement

interface VideoAsset : Asset {
	val video: HTMLVideoElement
}
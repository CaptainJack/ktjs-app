package ru.capjack.ktjs.app.assets

import org.w3c.dom.Document

interface XmlAsset : Asset {
	val document: Document
}
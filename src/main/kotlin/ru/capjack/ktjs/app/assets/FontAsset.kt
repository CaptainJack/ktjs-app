package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.assets.font.FontFace

interface FontAsset : Asset {
	val font: FontFace
}
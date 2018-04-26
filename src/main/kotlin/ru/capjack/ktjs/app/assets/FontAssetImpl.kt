package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.assets.font.FontFace

internal class FontAssetImpl(override val font: FontFace) : AbstractAsset(), FontAsset {
	override fun doDestroy() {
	}
}
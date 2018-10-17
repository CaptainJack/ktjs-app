package ru.capjack.ktjs.app.assets

@Suppress("unused")
sealed class AssetKind<A : Asset> {
	object Image : AssetKind<ImageAsset>()
	object Svg : AssetKind<SvgAsset>()
	object ImageAtlas : AssetKind<ImageAtlasAsset>()
	object Sound : AssetKind<SoundAsset>()
	object Xml : AssetKind<XmlAsset>()
	object Video : AssetKind<VideoAsset>()
	object Json : AssetKind<JsonAsset>()
}

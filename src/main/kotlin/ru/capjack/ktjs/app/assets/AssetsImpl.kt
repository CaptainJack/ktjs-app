package ru.capjack.ktjs.app.assets

open class AssetsImpl(private val map: AssetsMap) : Assets {
	override fun getImageAsset(name: String): ImageAsset = map.get(AssetKind.Image, name)
	
	override fun getSvgAsset(name: String): SvgAsset = map.get(AssetKind.Svg, name)
	
	override fun getAtlasAsset(name: String): ImageAtlasAsset = map.get(AssetKind.ImageAtlas, name)
	
	override fun getSoundAsset(name: String): SoundAsset = map.get(AssetKind.Sound, name)
	
	override fun getXmlAsset(name: String): XmlAsset = map.get(AssetKind.Xml, name)
	
	override fun getVideoAsset(name: String): VideoAsset = map.get(AssetKind.Video, name)
	
	override fun getJsonAsset(name: String): JsonAsset = map.get(AssetKind.Json, name)
	
	override fun getTexture(name: String) = getImageAsset(name).texture
	
	override fun getTexture(atlasName: String, frameName: String) = getAtlasAsset(atlasName)[frameName]
	
	override fun getSvgTexture(name: String) = getSvgAsset(name).texture
	
	override fun getSvgTexture(name: String, width: Int, height: Int) = getSvgAsset(name).getTexture(width, height)
	
	override fun getSound(name: String) = getSoundAsset(name).sound
	
	override fun getXml(name: String) = getXmlAsset(name).document
	
	override fun getVideo(name: String) = getVideoAsset(name).video
	
	override fun getJson(name: String) = getJsonAsset(name).content
	
	override fun destroy() {
		map.destroy()
	}
}
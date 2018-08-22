package ru.capjack.ktjs.app.assets

internal class AssetsImpl(
	private val images: Map<String, ImageAssetImpl>,
	private val sgvs: Map<String, SvgAssetImpl>,
	private val imageAtlases: Map<String, ImageAtlasAssetImpl>,
	private val sounds: Map<String, SoundAssetImpl>,
	private val xmls: Map<String, XmlAssetImpl>,
	private val videos: Map<String, VideoAssetImpl>,
	private val jsons: Map<String, JsonAssetImpl>
) : Assets {
	
	override fun getImageAsset(name: String): ImageAsset = fetchAsset(images, name)
	
	override fun getSvgAsset(name: String): SvgAsset = fetchAsset(sgvs, name)
	
	override fun getAtlasAsset(name: String): ImageAtlasAsset = fetchAsset(imageAtlases, name)
	
	override fun getSoundAsset(name: String): SoundAsset = fetchAsset(sounds, name)
	
	override fun getXmlAsset(name: String): XmlAsset = fetchAsset(xmls, name)
	
	override fun getVideoAsset(name: String): VideoAsset = fetchAsset(videos, name)
	
	override fun getJsonAsset(name: String): JsonAsset = fetchAsset(jsons, name)
	
	override fun getTexture(name: String) = getImageAsset(name).texture
	
	override fun getTexture(atlasName: String, frameName: String) = getAtlasAsset(atlasName)[frameName]
	
	override fun getSvgTexture(name: String, width: Int, height: Int) = getSvgAsset(name).getTexture(width, height)
	
	override fun getSound(name: String) = getSoundAsset(name).sound
	
	override fun getXml(name: String) = getXmlAsset(name).document
	
	override fun getVideo(name: String) = getVideoAsset(name).video
	
	override fun getJson(name: String) = getJsonAsset(name).content
	
	override fun destroy() {
		for (asset in images.values) {
			asset.destroy()
		}
	}
	
	private fun <A : Asset> fetchAsset(map: Map<String, A>, name: String): A {
		val asset = map[name]
		return asset ?: throw IllegalArgumentException("Asset named \"$name\" is not exist")
	}
}


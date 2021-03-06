package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.assets.font.FontFace
import ru.capjack.ktjs.app.assets.font.FontFaceRegistry
import ru.capjack.ktjs.app.display.DisplayRenderer
import ru.capjack.ktjs.common.progress.CompositeProgressRunner
import ru.capjack.ktjs.common.progress.Progress
import ru.capjack.ktjs.common.progress.ProgressRunner
import ru.capjack.ktjs.common.rl.FilePath
import ru.capjack.ktjs.common.rl.Url
import ru.capjack.ktjs.common.rl.Urls

internal class AssetsProducerImpl(
	private val renderer: DisplayRenderer,
	private val settings: AssetsSettings,
	private val baseUrl: Url = Urls.EMPTY,
	private val fontsBaseUrl: Url = Urls.EMPTY,
	private val fontsRegistry: FontFaceRegistry
) : AssetsCollector, AssetsProducer {
	
	private var fonts: MutableList<FontFace> = mutableListOf()
	private var imageMakers: MutableMap<String, ImageAssetMaker> = mutableMapOf()
	private var svgMakers: MutableMap<String, SvgAssetMaker> = mutableMapOf()
	private var imageAtlasMakers: MutableMap<String, ImageAtlasAssetMaker> = mutableMapOf()
	private var soundMakers: MutableMap<String, SoundAssetMaker> = mutableMapOf()
	private var xmlMakers: MutableMap<String, XmlAssetMaker> = mutableMapOf()
	private var videoMakers: MutableMap<String, VideoAssetMaker> = mutableMapOf()
	private var jsonMakers: MutableMap<String, JsonAssetMaker> = mutableMapOf()
	
	override fun addFont(face: FontFace): FontAsset {
		fontsRegistry[face]?.let {
			return it
		}
		
		val asset = FontAssetImpl(face)
		fonts.add(face)
		fontsRegistry[face] = asset
		
		return FontAssetImpl(face)
	}
	
	override fun addImage(name: String, path: FilePath): ImageAsset {
		return add(imageMakers, name, convertImagePath(path)) {
			ImageAssetMaker(it, renderer, settings)
		}
	}
	
	override fun addSvg(name: String, path: FilePath): SvgAsset {
		return add(svgMakers, name, path) {
			SvgAssetMaker(it, settings)
		}
	}
	
	override fun addImageAtlas(name: String, path: FilePath): ImageAtlasAsset {
		return add(imageAtlasMakers, name, path) {
			ImageAtlasAssetMaker(it, renderer, settings, ::convertImagePath)
		}
	}
	
	override fun addSound(name: String, path: FilePath): SoundAsset {
		return add(soundMakers, name, path) {
			SoundAssetMaker(it)
		}
	}
	
	override fun addXml(name: String, path: FilePath): XmlAsset {
		return add(xmlMakers, name, path) {
			XmlAssetMaker(it)
		}
	}
	
	override fun addVideo(name: String, path: FilePath): VideoAsset {
		return add(videoMakers, name, path) {
			VideoAssetMaker(it)
		}
	}
	
	override fun addJson(name: String, path: FilePath): JsonAsset {
		return add(jsonMakers, name, path) {
			JsonAssetMaker(it)
		}
	}
	
	private fun <A : AbstractAsset, M : AbstractAssetMaker<A>> add(makers: MutableMap<String, M>, name: String, path: FilePath, makerCreator: (Url) -> M): A {
		if (makers.containsKey(name)) {
			throw IllegalStateException("Asset \"$name\" is already added")
		}
		
		return makerCreator
			.invoke(baseUrl.resolvePath(path))
			.also { makers[name] = it }
			.asset
	}
	
	override fun load(): Progress {
		val loaders: MutableList<ProgressRunner> = mutableListOf()
		
		loaders.addAll(imageMakers.values)
		loaders.addAll(svgMakers.values)
		loaders.addAll(imageAtlasMakers.values)
		loaders.addAll(soundMakers.values)
		loaders.addAll(xmlMakers.values)
		loaders.addAll(videoMakers.values)
		loaders.addAll(jsonMakers.values)
		
		if (fonts.isNotEmpty()) {
			loaders.add(FontsLoader(fontsBaseUrl, fonts))
		}
		
		val progress = CompositeProgressRunner(loaders).run()
		return progress
	}
	
	override fun load(receiver: (Assets) -> Unit): Progress {
		
		val map = AssetsMap()
		
		imageMakers.forEach { map.add(AssetKind.Image, it.key, it.value.asset) }
		svgMakers.forEach { map.add(AssetKind.Svg, it.key, it.value.asset) }
		imageAtlasMakers.forEach { map.add(AssetKind.ImageAtlas, it.key, it.value.asset) }
		soundMakers.forEach { map.add(AssetKind.Sound, it.key, it.value.asset) }
		xmlMakers.forEach { map.add(AssetKind.Xml, it.key, it.value.asset) }
		videoMakers.forEach { map.add(AssetKind.Video, it.key, it.value.asset) }
		jsonMakers.forEach { map.add(AssetKind.Json, it.key, it.value.asset) }
		
		val collection = AssetsImpl(map)
		val progress = load()
		progress.onComplete { receiver(collection) }
		return progress
	}
	
	private fun convertImagePath(path: FilePath): FilePath {
		return if (!path.isFileExtension("svg")
			&& settings.bitmapResolutionResolveSuffix
			&& settings.imageResolution != 1
		) path.resolveSibling("${path.name.base}@${settings.imageResolution}x${path.name.extensionAsSuffix}")
		else path
	}
}


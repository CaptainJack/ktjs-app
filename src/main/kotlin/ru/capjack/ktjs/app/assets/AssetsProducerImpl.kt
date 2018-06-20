package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.assets.font.FontFace
import ru.capjack.ktjs.app.assets.font.FontFaceRegistry
import ru.capjack.ktjs.app.display.DisplayRenderer
import ru.capjack.ktjs.common.ifNotNull
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
	private var imageAtlasMakers: MutableMap<String, ImageAtlasAssetMaker> = mutableMapOf()
	private var soundMakers: MutableMap<String, SoundAssetMaker> = mutableMapOf()
	private var xmlMakers: MutableMap<String, XmlAssetMaker> = mutableMapOf()
	private var videoMakers: MutableMap<String, VideoAssetMaker> = mutableMapOf()
	private var jsonMakers: MutableMap<String, JsonAssetMaker> = mutableMapOf()
	
	override fun addFont(face: FontFace): FontAsset {
		fontsRegistry[face].ifNotNull {
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
	
	override fun addImageAtlas(name: String, path: FilePath): ImageAtlasAsset {
		return add(imageAtlasMakers, name, path) {
			ImageAtlasAssetMaker(it, renderer, settings)
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
		loaders.addAll(imageAtlasMakers.values)
		loaders.addAll(soundMakers.values)
		loaders.addAll(xmlMakers.values)
		loaders.addAll(videoMakers.values)
		
		if (fonts.isNotEmpty()) {
			loaders.add(FontsLoader(fontsBaseUrl, fonts))
		}
		
		return CompositeProgressRunner(loaders).run()
	}
	
	override fun load(receiver: (Assets) -> Unit): Progress {
		val collection = AssetsImpl(
			imageMakers.mapValues { it.value.asset },
			imageAtlasMakers.mapValues { it.value.asset },
			soundMakers.mapValues { it.value.asset },
			xmlMakers.mapValues { it.value.asset },
			videoMakers.mapValues { it.value.asset },
			jsonMakers.mapValues { it.value.asset }
		)
		val progress = load()
		progress.onComplete { receiver(collection) }
		return progress
	}
	
	private fun convertImagePath(path: FilePath): FilePath {
		return when {
			!path.isFileExtension("svg")
				&& settings.bitmapResolutionResolveSuffix
				&& settings.imageResolution != 1
			-> path.resolveSibling("${path.name.base}@${settings.imageResolution}x${path.name.extensionAsSuffix}")
			else -> path
		}
	}
}


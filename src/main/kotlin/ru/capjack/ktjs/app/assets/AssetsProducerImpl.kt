package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.app.assets.font.FontFace
import ru.capjack.ktjs.app.display.DisplayRenderer
import ru.capjack.ktjs.common.ifNotNull
import ru.capjack.ktjs.common.progress.CompositeProgressRunner
import ru.capjack.ktjs.common.progress.Progress
import ru.capjack.ktjs.common.progress.ProgressRunner
import ru.capjack.ktjs.common.rl.FilePath
import ru.capjack.ktjs.common.rl.FilePaths
import ru.capjack.ktjs.common.rl.Url
import ru.capjack.ktjs.common.rl.Urls

internal class AssetsProducerImpl(
	private val renderer: DisplayRenderer,
	private val settings: AssetsSettings,
	private val baseUrl: Url = Urls.EMPTY,
	private val fontsBaseUrl: Url = Urls.EMPTY,
	private val fontsRegistry: FontFaceRegistry
) : AbstractAssetsCollector(), AssetsProducer {
	
	private var imageMakers: MutableMap<String, ImageAssetMaker> = mutableMapOf()
	private var imageAtlasMakers: MutableMap<String, ImageAtlasAssetMaker> = mutableMapOf()
	private var fonts: MutableList<FontFace> = mutableListOf()
	private var soundMakers: MutableMap<String, SoundAssetMaker> = mutableMapOf()
	
	override fun addImage(name: String, path: FilePath): ImageAsset {
		if (imageMakers.containsKey(name)) {
			throw IllegalStateException("Asset \"$name\" is already added")
		}
		
		val maker = ImageAssetMaker(baseUrl.resolvePath(settings.resolveImagePath(path)), renderer, settings)
		
		imageMakers[name] = maker
		
		return maker.asset
	}
	
	override fun addImageAtlas(name: String, path: FilePath): ImageAtlasAsset {
		if (imageAtlasMakers.containsKey(name)) {
			throw IllegalStateException("Asset asset \"$name\" is already added")
		}
		
		val maker = ImageAtlasAssetMaker(baseUrl.resolvePath(path), renderer, settings)
		
		imageAtlasMakers[name] = maker
		
		return maker.asset
	}
	
	override fun addFont(face: FontFace): FontAsset {
		fontsRegistry[face].ifNotNull {
			return it
		}
		
		val asset = FontAssetImpl(face)
		fonts.add(face)
		fontsRegistry[face] = asset
		
		return FontAssetImpl(face)
	}
	
	override fun addSound(name: String, path: FilePath): SoundAsset {
		if (soundMakers.containsKey(name)) {
			throw IllegalStateException("Asset \"$name\" is already added")
		}
		
		val maker = SoundAssetMaker(baseUrl.resolvePath(path))
		
		soundMakers[name] = maker
		
		return maker.asset
	}
	
	override fun load(receiver: (Assets) -> Unit): Progress {
		val loaders: MutableList<ProgressRunner> = mutableListOf()
		
		loaders.addAll(imageMakers.values)
		loaders.addAll(imageAtlasMakers.values)
		loaders.addAll(soundMakers.values)
		
		if (fonts.isNotEmpty()) {
			loaders.add(FontsLoader(fontsBaseUrl, fonts))
		}
		
		val collection = AssetsImpl(
			imageMakers.mapValues { it.value.asset },
			imageAtlasMakers.mapValues { it.value.asset },
			soundMakers.mapValues { it.value.asset }
		)
		val progress = CompositeProgressRunner(loaders).run()
		progress.onComplete { receiver(collection) }
		
		return progress
	}
	
	private fun resolveFontPath(font: FontFace): Url {
		var file = font.family.replace(' ', '_')
		
		if (font.weight != FontFace.Weight.NORMAL) {
			file += "-${font.weight.value.capitalize()}"
		}
		if (font.style != FontFace.Style.NORMAL) {
			file += "-${font.style.value.capitalize()}"
		}
		
		return fontsBaseUrl.resolvePath(file)
	}
	
	override fun inDirectory(dir: String): AssetsCollector {
		return DirectoryAssetsCollector(this, FilePaths.get(dir))
	}
}


import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import ru.capjack.degos.publish.DegosPublishExtension

group = "ru.capjack.ktjs"

plugins {
	id("kotlin2js") version "1.2.60"
	id("ru.capjack.degos.publish") version "1.7.0"
	id("nebula.release") version "6.3.5"
}

repositories {
	maven("http://artifactory.capjack.ru/public")
}

dependencies {
	implementation(kotlin("stdlib-js"))
	implementation("ru.capjack.ktjs:ktjs-common:0.10.0")
	implementation("ru.capjack.ktjs:ktjs-wrapper-webfontloader:0.1.1")
	implementation("ru.capjack.ktjs:ktjs-wrapper-howler:0.2.0")
	implementation("ru.capjack.ktjs:ktjs-wrapper-pixi:0.7.0-SNAPSHOT")
}

degosPublish {
	publicationSources = DegosPublishExtension.PublicationSource.ALWAYS
}

tasks.withType<Kotlin2JsCompile> {
	kotlinOptions {
		moduleKind = "amd"
		sourceMap = true
		sourceMapEmbedSources = "always"
	}
}
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

group = "ru.capjack.ktjs"

plugins {
	id("kotlin2js") version "1.2.40"
	id("ru.capjack.degos.publish") version "1.5.0-dev.4+137b51a"
	id("nebula.release") version "6.0.0"
}

repositories {
	maven("http://artifactory.capjack.ru/public")
}

dependencies {
	implementation(kotlin("stdlib-js"))
	implementation("ru.capjack.ktjs:ktjs-common:0.1.0-SNAPSHOT")
	implementation("ru.capjack.ktjs:ktjs-wrapper-pixi:0.1.0-SNAPSHOT")
	implementation("ru.capjack.ktjs:ktjs-wrapper-webfontloader:0.1.0-SNAPSHOT")
	implementation("ru.capjack.ktjs:ktjs-wrapper-howler:0.1.0-SNAPSHOT")
	implementation("ru.capjack.ktjs:ktjs-wrapper-greensock:0.1.0-SNAPSHOT")
}

tasks.withType<Kotlin2JsCompile> {
	kotlinOptions {
		moduleKind = "amd"
	}
}
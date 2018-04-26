package ru.capjack.ktjs.app.display.misc

import ru.capjack.ktjs.app.display.nodes.Node

interface Animation {
	fun run(target: Node): AnimationProcess
}
package ru.capjack.ktjs.app.display.misc

import ru.capjack.ktjs.app.display.nodes.Node

interface AnimationProcess {
	fun addCompleteHandler(handler: (target: Node) -> Unit)
}
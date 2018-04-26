package ru.capjack.ktjs.app.display.misc

import ru.capjack.ktjs.app.display.nodes.Node

class DrivenAnimationProcess(private var target: Node) : AnimationProcess {
	
	private var completeHandlers: MutableList<(target: Node) -> Unit> = mutableListOf()
	
	override fun addCompleteHandler(handler: (target: Node) -> Unit) {
		completeHandlers.add(handler)
	}
	
	fun complete() {
		for (handler in completeHandlers) {
			handler(target)
		}
		completeHandlers.clear()
	}
}
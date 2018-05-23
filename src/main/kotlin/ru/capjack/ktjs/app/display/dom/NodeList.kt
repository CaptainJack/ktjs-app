package ru.capjack.ktjs.app.display.dom

interface NodeList {
	val nodes: List<Node>
	
	fun containsNode(node: Node): Boolean
	
	fun getNode(index: Int): Node
	
	fun addNode(node: Node): Boolean
	
	fun addNodes(nodes: List<Node>): Boolean
	
	fun addNodes(vararg nodes: Node): Boolean
	
	fun removeNode(node: Node): Boolean
	
	fun removeNodes(nodes: List<Node>): Boolean
	
	fun removeNodes(vararg nodes: Node): Boolean
	
	fun removeAllNodes(): Boolean
}
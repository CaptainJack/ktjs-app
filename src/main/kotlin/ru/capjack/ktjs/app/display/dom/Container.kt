package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.app.display.dom.traits.SpaceType
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.axial
import ru.capjack.ktjs.common.geom.mutableAxial
import ru.capjack.ktjs.common.geom.setMax
import ru.capjack.ktjs.wrapper.pixi.Container

abstract class Container : NodeOfContainer(), NodeList {
	final override val display = Container()
	
	override val nodes: List<Node>
		get() = _nodes
	
	private val _nodes: MutableList<Node> = mutableListOf()
	
	override fun getNode(index: Int): Node {
		return _nodes.elementAt(index)
	}
	
	override fun containsNode(node: Node): Boolean {
		return node.container == this
	}
	
	override fun addNode(node: Node): Boolean {
		if (doAddNode(node)) {
			processChangeNodes()
			return true
		}
		return false
	}
	
	override fun addNodes(nodes: List<Node>): Boolean {
		var changed = false
		for (node in nodes) {
			changed = doAddNode(node) || changed
		}
		if (changed) {
			processChangeNodes()
		}
		return changed
	}
	
	override fun addNodes(vararg nodes: Node): Boolean {
		return addNodes(nodes.asList())
	}
	
	override fun removeNode(node: Node): Boolean {
		if (doRemoveNode(node)) {
			processChangeNodes()
			return true
		}
		return false
	}
	
	override fun removeNodes(nodes: List<Node>): Boolean {
		var changed = false
		for (node in nodes) {
			changed = doRemoveNode(node) || changed
		}
		if (changed) {
			processChangeNodes()
		}
		return changed
	}
	
	override fun removeNodes(vararg nodes: Node): Boolean {
		return removeNodes(nodes.asList())
	}
	
	override fun removeAllNodes(): Boolean {
		if (_nodes.isEmpty()) {
			return false
		}
		
		val nodes = _nodes.toTypedArray()
		_nodes.clear()
		
		for (node in nodes) {
			display.removeChild(node.display)
			node.container = null
		}
		
		processChangeNodes()
		return true
	}
	
	private val updateContentSizeRef = ::updateContentSize
	
	private fun doAddNode(node: Node): Boolean {
		if (containsNode(node)) {
			return false
		}
		node.container = this
		node.size.onChange(updateContentSizeRef)
		
		_nodes.add(node)
		display.addChild(node.display)
		
		return true
	}
	
	private fun doRemoveNode(node: Node): Boolean {
		if (_nodes.remove(node)) {
			display.removeChild(node.display)
			node.container = null
			node.size.offChange(updateContentSizeRef)
			return true
		}
		return false
	}
	
	protected open fun processChangeNodes() {
		updateContentSize()
	}
	
	protected open fun updateContentSize() {
		val ai = axial { sizeRule.isApplicable(SpaceType.INSIDE, it) }
		val cs = mutableAxial(0)
		
		for (node in nodes) {
			Axis.forEach {
				cs.setMax(it, node.size[it] + if (ai[it] && node.positionRule.isApplicable(SpaceType.OUTSIDE, it)) 0 else node.position[it])
			}
		}
		
		_contentSize.set(cs)
	}
	
}
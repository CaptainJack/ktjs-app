package ru.capjack.ktjs.app.display.nodes

import ru.capjack.ktjs.app.display.misc.Animation
import ru.capjack.ktjs.app.display.nodes.box.Layout
import ru.capjack.ktjs.app.display.nodes.box.Layouts
import ru.capjack.ktjs.app.display.nodes.node.SizeExpansion
import ru.capjack.ktjs.app.display.nodes.node.SizeRules
import ru.capjack.ktjs.common.Handler
import ru.capjack.ktjs.common.HandlerDummy
import ru.capjack.ktjs.common.HandlerOfFunction
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.Insets
import ru.capjack.ktjs.common.geom.InsetsInstances
import ru.capjack.ktjs.common.geom.MutableAxialValues
import ru.capjack.ktjs.common.geom.MutableAxialValuesImpl
import ru.capjack.ktjs.common.geom.setMax
import ru.capjack.ktjs.wrapper.pixi.Container
import kotlin.reflect.KProperty

open class Box(
	val layout: Layout = Layouts.FREE
) : NodeOfContainer<Container>(Container()) {
	
	var padding: Insets<Int> = InsetsInstances.INT_0
		set(value) {
			if (field != value) {
				field = value
				_innerSize = if (value.isEquals(0)) size else InnerSize()
			}
		}
	
	val innerSize: MutableAxialValues<Int>
		get() = _innerSize
	
	val contentSize: AxialValues<Int>
		get() = _contentSize
	
	val children: List<Node>
		get() = _children
	
	private val _contentSize: MutableAxialValues<Int> = MutableAxialValuesImpl(0)
	
	private val _children: MutableList<Node> = mutableListOf()
	
	private var _innerSize: MutableAxialValues<Int> = size
	
	private val childSizeChangeHandler: Handler = if (layout.dependentOnChildSizeChange) HandlerOfFunction(::processChildSizeChanged) else HandlerDummy
	
	private var layoutApplying: Boolean = false
	
	init {
		sizeRule = SizeRules.STRETCHING
	}
	
	override fun destroy() {
		val nodes = _children.toTypedArray()
		
		removeChildren()
		
		for (child in nodes) {
			child.destroy()
		}
		
		super.destroy()
	}
	
	fun getChild(index: Int): Node {
		return _children.elementAt(index)
	}
	
	fun containsChild(node: Node): Boolean {
		return node.parent == this || _children.contains(node)
	}
	
	fun addChild(node: Node, index: Int = -1, animation: Animation? = null): Boolean {
		if (doAddChild(node, index, animation)) {
			processChildrenChanged()
			return true
		}
		return false
	}
	
	fun addChildren(nodes: List<Node>, index: Int = -1): Boolean {
		val toIndex = index != -1
		var i = index
		var changed = false
		for (node in nodes) {
			changed = doAddChild(node, i) || changed
			if (changed && toIndex) {
				++i
			}
		}
		if (changed) {
			processChildrenChanged()
		}
		return changed
	}
	
	fun addChildren(vararg nodes: Node, index: Int = -1): Boolean {
		return addChildren(nodes.asList(), index)
	}
	
	fun removeChild(node: Node, animation: Animation? = null): Boolean {
		if (doRemoveChild(node, animation)) {
			processChildrenChanged()
			return true
		}
		return false
	}
	
	fun removeChildren(nodes: List<Node>): Boolean {
		var changed = false
		for (node in nodes) {
			changed = doRemoveChild(node) || changed
		}
		if (changed) {
			processChildrenChanged()
		}
		return changed
	}
	
	fun removeChildren(vararg nodes: Node): Boolean {
		return removeChildren(nodes.asList())
	}
	
	fun removeChildren(): Boolean {
		if (_children.isEmpty()) {
			return false
		}
		
		val nodes = _children.toTypedArray()
		_children.clear()
		
		for (node in nodes) {
			view.removeChild(node.view)
			node.parent = null
			processChildRemoved(node)
		}
		
		processChildrenChanged()
		return true
	}
	
	private fun doRemoveChild(node: Node, animation: Animation? = null): Boolean {
		if (_children.remove(node)) {
			if (animation == null) {
				view.removeChild(node.view)
			}
			else {
				animation.run(node).addCompleteHandler({
					view.removeChild(node.view)
				})
			}
			
			node.parent = null
			processChildRemoved(node)
			return true
		}
		return false
	}
	
	private fun doAddChild(node: Node, index: Int = -1, animation: Animation? = null): Boolean {
		if (containsChild(node)) {
			return false
		}
		node.parent = this
		
		if (index == -1) {
			_children.add(node)
			view.addChild(node.view)
		}
		else {
			_children.add(index, node)
			view.addChildAt(node.view, index)
		}
		
		processChildAdded(node)
		
		if (animation != null) {
			animation.run(node)
		}
		return true
	}
	
	override fun processSizeChanged() {
		applyLayout()
	}
	
	private fun processChildAdded(node: Node) {
		if (!layout.dependentOnChildrenChange) {
			layout.apply(node, size)
			updateContentSize()
		}
		
		if (layout.dependentOnChildSizeChange) {
			node.size.addChangeHandler(childSizeChangeHandler)
		}
	}
	
	private fun processChildRemoved(node: Node) {
		if (layout.dependentOnChildSizeChange) {
			node.size.removeChangeHandler(childSizeChangeHandler)
		}
	}
	
	private fun processChildrenChanged() {
		if (layout.dependentOnChildrenChange) {
			applyLayout()
		}
	}
	
	private fun processChildSizeChanged() {
		if (layout.dependentOnChildSizeChange) {
			applyLayout()
		}
	}
	
	private fun applyLayout() {
		if (!layoutApplying && _children.isNotEmpty()) {
			layoutApplying = true
			layout.apply(_children, _innerSize)
			layoutApplying = false
			updateContentSize()
		}
	}
	
	private fun updateContentSize() {
		_contentSize.set(0, 0)
		
		for (node in _children) {
			_contentSize.setMax(
				node.position.horizontal + node.size.horizontal,
				node.position.vertical + node.size.vertical
			)
		}
		
		sizeRule.apply(_innerSize, _contentSize, SizeExpansion.STRETCHING)
	}
	
	
	private inner class InnerSize : MutableAxialValues<Int> {
		override var horizontal: Int by Delegate(Axis.HORIZONTAL)
		override var vertical: Int by Delegate(Axis.VERTICAL)
		
		private inner class Delegate(private val axis: Axis) {
			operator fun getValue(innerSize: InnerSize, property: KProperty<*>): Int {
				return (size[axis] - padding[axis]).coerceAtLeast(0)
			}
			
			operator fun setValue(innerSize: InnerSize, property: KProperty<*>, value: Int) {
				size[axis] = value + padding[axis]
			}
		}
	}
}



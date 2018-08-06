package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.app.display.dom.traits.Dimension
import ru.capjack.ktjs.app.display.dom.traits.DimensionRule
import ru.capjack.ktjs.app.display.dom.traits.PositionRule
import ru.capjack.ktjs.app.display.dom.traits.PositionRules
import ru.capjack.ktjs.app.display.dom.traits.SizeImp
import ru.capjack.ktjs.app.display.dom.traits.SizeRule
import ru.capjack.ktjs.app.display.dom.traits.SizeRules
import ru.capjack.ktjs.app.display.dom.traits.SpaceType
import ru.capjack.ktjs.common.Cancelable
import ru.capjack.ktjs.common.CancelableDummy
import ru.capjack.ktjs.common.Delegates.observable
import ru.capjack.ktjs.common.Destroyable
import ru.capjack.ktjs.common.geom.Axial
import ru.capjack.ktjs.common.geom.AxialInstances
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.ChangeableAxial
import ru.capjack.ktjs.common.geom.MutableChangeableAxialImpl
import ru.capjack.ktjs.wrapper.pixi.Graphics
import ru.capjack.ktjs.wrapper.pixi.filters.AlphaFilter
import ru.capjack.ktjs.wrapper.pixi.set

abstract class Node : Destroyable {
	val coordinate: Dimension
		get() = _coordinate.unsafeCast<Dimension>()
	
	val position: Dimension
		get() = _position.unsafeCast<Dimension>()
	
	val size: Dimension
		get() = _size
	
	val contentSize: ChangeableAxial<Int>
		get() = mutableContentSize
	
	var positionRule: PositionRule
		by observable(PositionRules.START, ::processChangePositionRule)
	
	var sizeRule: SizeRule
		by observable(SizeRules.NOTHING, ::processChangeSizeRule)
	
	var visible: Boolean
		get() = display.visible
		set(value) {
			display.visible = value
		}
	
	var alpha: Double by observable(1.0, ::processChangeAlpha)
	
	abstract val display: ru.capjack.ktjs.wrapper.pixi.Container
	
	protected val mutableContentSize = MutableChangeableAxialImpl(0, 0)
	
	private val _coordinate = MutableChangeableAxialImpl(0, 0)
	private val _position = MutableChangeableAxialImpl(0, 0)
	private val _size = SizeImp()
	
	private var positionRuleBindingInside: Cancelable = CancelableDummy
	private var positionRuleBindingOutside: Cancelable = CancelableDummy
	private var sizeRuleBindingInside: Cancelable = CancelableDummy
	private var sizeRuleBindingOutside: Cancelable = CancelableDummy
	
	private var alphaFilter: AlphaFilter? = null
	
	internal var container: Container? = null
		set(value) {
			if (field != value) {
				field?.also {
					field = null
					it.removeNode(this)
				}
				field = value
				processChangeContainer()
			}
		}
	
	init {
		position.onChange(::applyPositionRule)
		coordinate.onChange { display.position.set(coordinate) }
	}
	
	fun showDebugBackground(color: Int = 0xFF0000) {
		val g = Graphics().apply { }
		
		fun draw() {
			g.clear().beginFill(color, 0.2).drawRect(0, 0, size.x, size.y).endFill()
		}
		
		draw()
		size.onChange(::draw)
		display.addChildAt(g, 0)
	}
	
	fun applyPositionRule(rule: PositionRule, space: Axial<Int>, axis: Axis) {
		rule.apply(coordinate, position, space, size, axis)
	}
	
	override fun destroy() {
		container = null
		_position.destroy()
		_size.destroy()
		mutableContentSize.destroy()
		display.destroy()
	}
	
	protected open fun processChangeContainer() {
		applyPositionRule()
		processChangePositionRule()
		processChangeSizeRule()
	}
	
	protected open fun processChangePositionRule() {
		applyPositionRule()
		bindPositionRuleInside()
		bindPositionRuleOutsize()
	}
	
	protected open fun processChangeSizeRule() {
		bindSizeRuleInside()
		bindSizeRuleOutsize()
	}
	
	private fun bindRule(rule: DimensionRule, type: SpaceType, binder: () -> Cancelable): Cancelable {
		return when (rule.isApplicable(type)) {
			true -> binder.invoke()
			else -> CancelableDummy
		}
	}
	
	private fun bindPositionRuleInside() {
		positionRuleBindingInside.cancel()
		container?.also {
			positionRuleBindingInside = bindRule(positionRule, SpaceType.INSIDE) {
				size.onChange(::applyPositionRule)
			}
		}
	}
	
	private fun bindPositionRuleOutsize() {
		positionRuleBindingOutside.cancel()
		container?.also {
			positionRuleBindingOutside = bindRule(positionRule, SpaceType.OUTSIDE) {
				it.size.onChange(::applyPositionRule)
			}
		}
	}
	
	private fun bindSizeRuleInside() {
		sizeRuleBindingInside.cancel()
		sizeRuleBindingInside = bindRule(sizeRule, SpaceType.INSIDE) {
			applySizeRuleInside()
			mutableContentSize.onChange(::applySizeRuleInside)
		}
	}
	
	private fun bindSizeRuleOutsize() {
		sizeRuleBindingOutside.cancel()
		container?.also {
			sizeRuleBindingOutside = bindRule(sizeRule, SpaceType.OUTSIDE) {
				applySizeRuleOutside()
				it.size.onChange(::applySizeRuleOutside)
			}
		}
	}
	
	private fun applyPositionRule() {
		positionRule.apply(coordinate, position, container?.size ?: AxialInstances.INT_0, size)
	}
	
	private fun applySizeRuleInside() {
		sizeRule.apply(size, mutableContentSize, SpaceType.INSIDE)
	}
	
	private fun applySizeRuleOutside() {
		container?.also {
			sizeRule.apply(size, it.size, SpaceType.OUTSIDE)
		}
	}
	
	private fun processChangeAlpha(value: Double) {
		if (value == 1.0) {
			alphaFilter = null
			display.filters = emptyArray()
		}
		else {
			if (alphaFilter == null) {
				alphaFilter = AlphaFilter(value)
				display.filters = arrayOf(alphaFilter!!)
			}
			else {
				alphaFilter!!.alpha = value
			}
		}
	}
}
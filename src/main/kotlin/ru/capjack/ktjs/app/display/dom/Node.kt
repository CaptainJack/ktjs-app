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
import ru.capjack.ktjs.common.geom.ChangeableAxial
import ru.capjack.ktjs.common.geom.MutableChangeableAxialImpl
import ru.capjack.ktjs.wrapper.pixi.DisplayObject
import ru.capjack.ktjs.wrapper.pixi.filters.AlphaFilter
import ru.capjack.ktjs.wrapper.pixi.set

abstract class Node : Destroyable {
	val position: Dimension
		get() = _position.unsafeCast<Dimension>()
	
	val size: Dimension
		get() = _size
	
	val contentSize: ChangeableAxial<Int>
		get() = _contentSize
	
	var positionRule: PositionRule
		by observable(PositionRules.NOTHING, ::processChangePositionRule)
	
	var sizeRule: SizeRule
		by observable(SizeRules.NOTHING, ::processChangeSizeRule)
	
	var visible: Boolean
		get() = display.visible
		set(value) {
			display.visible = value
		}
	
	var alpha: Double by observable(1.0, ::processChangeAlpha)
	
	abstract val display: DisplayObject
	
	protected val _contentSize = MutableChangeableAxialImpl(0, 0)
	
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
		position.onChange {
			display.position.set(position)
		}
	}
	
	override fun destroy() {
		container = null
		_position.destroy()
		_size.destroy()
		_contentSize.destroy()
		display.destroy()
	}
	
	protected open fun processChangeContainer() {
		processChangePositionRule()
		processChangeSizeRule()
	}
	
	protected open fun processChangePositionRule() {
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
				applyPositionRule()
				size.onChange(::applyPositionRule)
			}
		}
	}
	
	private fun bindPositionRuleOutsize() {
		positionRuleBindingOutside.cancel()
		container?.also {
			positionRuleBindingOutside = bindRule(positionRule, SpaceType.OUTSIDE) {
				applyPositionRule()
				it.size.onChange(::applyPositionRule)
			}
		}
	}
	
	private fun bindSizeRuleInside() {
		sizeRuleBindingInside.cancel()
		sizeRuleBindingInside = bindRule(sizeRule, SpaceType.INSIDE) {
			applySizeRuleInside()
			contentSize.onChange(::applySizeRuleInside)
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
		container?.also {
			positionRule.apply(position, it.size, size)
		}
	}
	
	private fun applySizeRuleInside() {
		sizeRule.apply(size, _contentSize, SpaceType.INSIDE)
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
		} else {
			if (alphaFilter == null) {
				alphaFilter = AlphaFilter(value)
				display.filters = arrayOf(alphaFilter!!)
			} else {
				alphaFilter!!.alpha = value
			}
		}
	}
}
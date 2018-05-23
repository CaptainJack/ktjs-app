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
import ru.capjack.ktjs.common.Destroyable
import ru.capjack.ktjs.common.geom.ChangeableAxialValues
import ru.capjack.ktjs.common.geom.MutableChangeableAxialValuesImpl
import ru.capjack.ktjs.common.observable
import ru.capjack.ktjs.wrapper.pixi.DisplayObject
import ru.capjack.ktjs.wrapper.pixi.set

abstract class Node : Destroyable {
	val position: Dimension get() = _position.unsafeCast<Dimension>()
	val size: Dimension get() = _size
	val contentSize: ChangeableAxialValues<Int> get() = _contentSize
	
	var positionRule: PositionRule by observable(PositionRules.NOTHING, ::processPositionRuleChanged)
	var sizeRule: SizeRule by observable(SizeRules.NOTHING, ::processSizeRuleChanged)
	
	abstract val display: DisplayObject
	
	protected val _contentSize = MutableChangeableAxialValuesImpl(0, 0)
	
	private val _position = MutableChangeableAxialValuesImpl(0, 0)
	private val _size = SizeImp()
	
	private var positionRuleBindingInside: Cancelable = CancelableDummy
	private var positionRuleBindingOutside: Cancelable = CancelableDummy
	private var sizeRuleBindingInside: Cancelable = CancelableDummy
	private var sizeRuleBindingOutside: Cancelable = CancelableDummy
	
	internal var container: Container? = null
		set(value) {
			if (field != value) {
				field?.also {
					field = null
					it.removeNode(this)
				}
				field = value
				processContainerChanged()
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
	
	private fun processContainerChanged() {
		processPositionRuleChanged()
		processSizeRuleChanged()
	}
	
	protected open fun processPositionRuleChanged() {
		bindPositionRuleInside()
		bindPositionRuleOutsize()
	}
	
	protected open fun processSizeRuleChanged() {
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
}
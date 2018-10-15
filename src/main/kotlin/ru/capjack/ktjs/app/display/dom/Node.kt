package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.app.display.Stage
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
import ru.capjack.ktjs.common.geom.*
import ru.capjack.ktjs.wrapper.pixi.Filter
import ru.capjack.ktjs.wrapper.pixi.Graphics
import ru.capjack.ktjs.wrapper.pixi.filters.AlphaFilter
import ru.capjack.ktjs.wrapper.pixi.set
import kotlin.reflect.KClass

abstract class Node : Destroyable {
	val coordinate: Dimension
		get() = _coordinate.unsafeCast<Dimension>()
	
	val position: Dimension
		get() = _position.unsafeCast<Dimension>()
	
	val size: Dimension
		get() = _size
	
	val contentSize: ChangeableAxial<Int>
		get() = mutableContentSize
	
	val innerSize: Axial<Int> = object : AbstractAxial<Int>() {
		override val x: Int get() = size.x - padding.size.x
		override val y: Int get() = size.y - padding.size.y
	}
	
	var padding: Insets<Int>
		by observable(InsetsInstances.INT_0, ::processChangePadding)
	
	var positionRule: PositionRule
		by observable(PositionRules.START, ::processChangePositionRule)
	
	var sizeRule: SizeRule
		by observable(SizeRules.NOTHING, ::processChangeSizeRule)
	
	var visible: Boolean
		get() = display.visible
		set(value) {
			display.visible = value
		}
	
	val stage: Stage?
		get() = getState()
	
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
				val oldStage = stage
				field?.also {
					field = null
					it.removeNode(this)
				}
				field = value
				processChangeContainer()
				if (oldStage != stage) {
					processChangeStage(oldStage)
				}
			}
		}
	
	init {
		_position.onChange(::applyPositionRule)
		_coordinate.onChange(::assignDisplayPosition)
	}
	
	private fun assignDisplayPosition() {
		val offset = padding.leftTop
		display.position.set { _coordinate[it] + offset[it] }
	}
	
	fun showDebugBackground(color: Int = 0xFF0000) {
		val g = Graphics()
		
		fun draw() {
			g.clear()
				.beginFill(color, 0.2)
				.drawRect(
					-padding.left,
					-padding.top,
					size.x,
					size.y
				)
				.endFill()
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
	
	internal open fun getState(): Stage? {
		return container?.stage
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
	
	protected open fun processChangeStage(old: Stage?) {
		if (old == null) {
			processAddedToStage()
		}
		else if (stage == null) {
			processRemovedFromStage()
		}
	}
	
	protected open fun processAddedToStage() {
		fixFilters()
	}
	
	protected open fun processRemovedFromStage() {}
	
	protected open fun processChangePadding() {
		assignDisplayPosition()
		tryApplySizeRuleInside()
	}
	
	internal fun callProcessAddedToStage() {
		processAddedToStage()
	}
	
	internal fun callProcessRemovedFromStage() {
		processRemovedFromStage()
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
		positionRule.apply(coordinate, position, container?.innerSize ?: AxialInstances.INT_0, size)
	}
	
	private fun applySizeRuleInside() {
		val ps = padding.size
		val space =
			if (ps.isEquals(0)) mutableContentSize
			else axial { mutableContentSize[it] + ps[it] }
		
		sizeRule.apply(size, space, SpaceType.INSIDE)
	}
	
	private fun applySizeRuleOutside() {
		container?.also { con: Container ->
			val ax = con.isAllowsChildrenSizeOutside(Axis.X)
			val ay = con.isAllowsChildrenSizeOutside(Axis.Y)
			if (ax && ay) {
				sizeRule.apply(size, con.innerSize, SpaceType.OUTSIDE)
			}
			else if (ax) {
				sizeRule.apply(size, con.innerSize, SpaceType.OUTSIDE, Axis.X)
			}
			else if (ay) {
				sizeRule.apply(size, con.innerSize, SpaceType.OUTSIDE, Axis.Y)
			}
		}
	}
	
	private fun tryApplySizeRuleInside() {
		if (sizeRule.isApplicable(SpaceType.INSIDE)) {
			applySizeRuleInside()
		}
	}
	
	private fun processChangeAlpha(value: Double) {
		if (value == 1.0) {
			alphaFilter?.also {
				removeFilter(it)
				alphaFilter = null
			}
		}
		else {
			if (alphaFilter == null) {
				alphaFilter = AlphaFilter(value)
				addFilter(alphaFilter!!)
			}
			else {
				alphaFilter!!.alpha = value
			}
		}
	}
	
	fun addFilter(filter: Filter) {
		if (display.filters?.contains(filter) == true) {
			return
		}
		fixFilter(filter)
		display.filters = display.filters?.plus(filter) ?: arrayOf(filter)
	}
	
	fun removeFilter(filter: Filter) {
		display.filters?.also { filters ->
			display.filters = if (filters.size == 1 && filters[0] == filter) {
				null
			}
			else {
				filters.filterNot { it == filter }.toTypedArray()
			}
		}
	}
	
	fun removeFilter(type: KClass<out Filter>) {
		display.filters?.also { filters ->
			val list = filters.filter { type.isInstance(it) }
			display.filters = if (list.isEmpty()) null else list.toTypedArray()
		}
	}
	
	fun clearFilters() {
		display.filters = null
	}
	
	private fun fixFilter(filter: Filter) {
		stage?.also { filter.resolution = it.displaySystem.renderer.resolution }
	}
	
	private fun fixFilters() {
		val filters = display.filters
			?: return
		
		@Suppress("ReplaceSingleLineLet")
		val r = stage?.let { it.displaySystem.renderer.resolution }
			?: return
		
		for (filter in filters) {
			filter.resolution = r
		}
	}
}
package ru.capjack.ktjs.app.display.nodes.box

import ru.capjack.ktjs.app.display.nodes.Node
import ru.capjack.ktjs.app.display.nodes.node.PositionRule
import ru.capjack.ktjs.app.display.nodes.node.PositionRules
import ru.capjack.ktjs.app.display.nodes.node.SizeExpansion
import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.AxialValuesImpl
import ru.capjack.ktjs.common.geom.MutableAxialValues
import ru.capjack.ktjs.common.geom.MutableAxialValuesImpl
import ru.capjack.ktjs.common.repeat
import kotlin.math.ceil

class GridLayout(
	private var cellSize: AxialValues<Int>,
	private var minGaps: AxialValues<Int>,
	dynamic: Boolean = false,
	private var positionRuleInCell: PositionRule = PositionRules.NODE
) : Layout {
	
	override val dependentOnChildrenChange: Boolean = true
	override val dependentOnChildSizeChange: Boolean = dynamic
	
	override fun apply(nodes: List<Node>, space: AxialValues<Int>) {
		if (nodes.isEmpty()) {
			return
		}
		
		val cellsAmount = getAxialCellsAmount(nodes.size, space)
		
		val horizontalGap = getFillingHorizontalGap(cellsAmount, space)
		
		val position: MutableAxialValues<Int> = MutableAxialValuesImpl(horizontalGap, 0)
		
		nodes.size.repeat {
			val node = nodes[it]
			node.sizeRule.apply(node.size, cellSize, SizeExpansion.FILLING)
			if (it > 0 && it % cellsAmount.horizontal == 0) {
				position.vertical += cellSize.vertical + minGaps.vertical
				position.horizontal = horizontalGap
			}
			positionRuleInCell.apply(node, cellSize)
			node.position.horizontal = position.horizontal
			node.position.vertical = position.vertical
			position.horizontal += cellSize.horizontal + horizontalGap
		}
	}
	
	override fun apply(node: Node, space: AxialValues<Int>) {
	}
	
	private fun getAxialCellsAmount(elementsAmount: Int, space: AxialValues<Int>): AxialValues<Int> {
		val restSpace = space.horizontal - cellSize.horizontal - minGaps.horizontal * 2
		var columns: Int = 1 + restSpace / (cellSize.horizontal + minGaps.horizontal)
		if (columns < 1) {
			columns = 1
		}
		val rows = ceil(elementsAmount / columns.toDouble()).toInt()
		return AxialValuesImpl(columns, rows)
	}
	
	
	private fun getFillingHorizontalGap(cellsAmount: AxialValues<Int>, space: AxialValues<Int>): Int {
		return minGaps.horizontal + calculateRestDeltaSpace(cellsAmount.horizontal, space.horizontal)
	}
	
	private fun calculateRestDeltaSpace(columnsAmount: Int, space: Int): Int {
		val restSpace: Int = space - columnsAmount * (cellSize.horizontal + minGaps.horizontal) - minGaps.horizontal
		return restSpace / (columnsAmount + 1)
	}
}
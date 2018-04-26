package ru.capjack.ktjs.app.display.nodes.node

import ru.capjack.ktjs.common.geom.AxialValues
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.MutableAxialValues

interface SizeRule {
	fun isExpansion(expansion: SizeExpansion): Boolean
	
	fun isExpansion(expansion: SizeExpansion, axis: Axis): Boolean
	
	fun apply(target: MutableAxialValues<Int>, space: AxialValues<Int>, expansion: SizeExpansion) {
		if (isExpansion(expansion)) {
			target.set(space)
		}
		else {
			Axis.forEach {
				if (isExpansion(expansion, it)) {
					target[it] = space[it]
				}
			}
		}
	}
}
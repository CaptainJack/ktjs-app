package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.app.display.dom.traits.SpaceType
import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.common.geom.axial
import ru.capjack.ktjs.common.geom.mutableAxial
import ru.capjack.ktjs.common.geom.setMax

open class Box : Container() {
	override fun processChangeNodes() {
		super.processChangeNodes()
		
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
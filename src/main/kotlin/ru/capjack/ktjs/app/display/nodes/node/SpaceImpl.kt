package ru.capjack.ktjs.app.display.nodes.node

import ru.capjack.ktjs.common.geom.Axis
import ru.capjack.ktjs.app.display.misc.AdjustableOfFunctionChangeableAxialValues

internal class SpaceImpl(
	adjuster: (axis: Axis, value: Int) -> Int
) : AdjustableOfFunctionChangeableAxialValues<Int>(adjuster, 0), Space


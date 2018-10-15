package ru.capjack.ktjs.app.display.dom

import ru.capjack.ktjs.app.display.misc.TextStyle
import ru.capjack.ktjs.common.ChangeableValue

class ChangeableText<V>(
	style: TextStyle,
	value: ChangeableValue<V>,
	private val formatter: (V) -> String
) : Text(
	style,
	formatter(value.value)
) {
	private val updater = value.onChange(::updateValue)
	
	private fun updateValue(v: V) {
		value = formatter.invoke(v)
	}
	
	override fun destroy() {
		updater.cancel()
		super.destroy()
	}
}
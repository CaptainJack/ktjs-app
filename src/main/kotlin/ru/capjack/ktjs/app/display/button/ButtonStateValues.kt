package ru.capjack.ktjs.app.display.button

open class ButtonStateValues<out T>(
	val idle: T,
	val focus: T = idle,
	val active: T = focus,
	val disabled: T = idle
) {
	operator fun get(state: ButtonState) = when (state) {
		ButtonState.IDLE     -> idle
		ButtonState.FOCUS    -> focus
		ButtonState.ACTIVE   -> active
		ButtonState.DISABLED -> disabled
	}
	
	inline fun <R> map(transform: (T) -> R) = ButtonStateValues(
		transform(idle),
		transform(focus),
		transform(active),
		transform(disabled)
	)
	
	inline fun <R> map(transform: (ButtonState, T) -> R) = ButtonStateValues(
		transform(ButtonState.IDLE, idle),
		transform(ButtonState.FOCUS, focus),
		transform(ButtonState.ACTIVE, active),
		transform(ButtonState.DISABLED, disabled)
	)
}
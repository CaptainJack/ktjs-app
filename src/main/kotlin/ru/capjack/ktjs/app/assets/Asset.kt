package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.common.Destroyable

interface Asset : Destroyable {
	val loaded: Boolean
}
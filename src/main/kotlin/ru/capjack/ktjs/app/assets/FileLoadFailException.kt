package ru.capjack.ktjs.app.assets

import ru.capjack.ktjs.common.js.convertErrorEventToString
import ru.capjack.ktjs.common.rl.Url

class FileLoadFailException(url: Url, message: dynamic) :
	RuntimeException("Failed to load image '$url' (${if (message is String) message.unsafeCast<String>() else convertErrorEventToString(message)})")
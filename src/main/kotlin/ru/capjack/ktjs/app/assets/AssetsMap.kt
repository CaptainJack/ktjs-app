package ru.capjack.ktjs.app.assets

class AssetsMap {
	
	private val map = mutableMapOf<AssetKind<*>, MutableMap<String, Asset>>()
	
	fun <A : Asset> add(kind: AssetKind<A>, name: String, asset: A) {
		map.getOrPut(kind) { mutableMapOf() }[name] = asset
	}
	
	fun <A : Asset> get(kind: AssetKind<A>, name: String): A {
		return map[kind]?.get(name)?.unsafeCast<A>() ?: throw IllegalArgumentException("Asset named '$name' is not exist")
	}
	
	fun destroy() {
		map.values.forEach {
			it.values.forEach(Asset::destroy)
			it.clear()
		}
		map.clear()
	}
}
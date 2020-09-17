package com.example.configchangewarehouse

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

object ConfigChangeWarehouse {
	private const val BUNDLE_KEY = "ConfigChangeWarehouse"

	const val DEFAULT_TAG = "default"

	inline fun <reified T> getOrStore(key: Int, crossinline initializer: () -> T): T {
		return getOrStoreTagged(DEFAULT_TAG, key, initializer)
	}

	inline fun <reified T> getOrStoreTagged(tag: String, key: Int, crossinline initializer: () -> T): T {
		val innerKey = T::class.java.simpleName + tag
		if (!Warehouse.of(key).contains(innerKey)) {
			Warehouse.of(key)[innerKey] = initializer() as Any
		}
		return Warehouse.of(key)[innerKey] as T
	}

	fun attach(activity: Activity, savedInstanceState: Bundle?) {
		attachInner(activity, savedInstanceState)
	}

	fun attach(fragment: Fragment, savedInstanceState: Bundle?) {
		attachInner(fragment, savedInstanceState)
	}

	fun onSaveInstanceState(outState: Bundle, key: Int) {
		outState.putInt(BUNDLE_KEY, key)
	}

	fun checkCleanUp(activity: Activity, key: Int) {
		if (!activity.isChangingConfigurations) {
			Warehouse.clear(key)
		}
	}

	private fun attachInner(obj: Any, savedInstanceState: Bundle?): Int {
		var key = -1
		if (savedInstanceState != null) {
			key = savedInstanceState.getInt(BUNDLE_KEY, -1)
			if (key != -1) {
				return key
			}
		}
		if (key == -1) {
			key = obj.hashCode()
		}
		while (Warehouse.contains(key)) {
			key++
		}
		return key
	}
}

object Warehouse {
	private val maps = mutableMapOf<Int, MutableMap<String, Any>>()

	fun contains(key: Int) = maps.contains(key)

	fun of(key: Int): MutableMap<String, Any> {
		if (!maps.contains(key)) {
			maps[key] = mutableMapOf()
			Log.w("###", "create store for key=$key; current keys=${maps.keys}")
		}
		return maps[key]!!
	}

	fun clear(key: Int) {
		maps.remove(key)
		Log.w("###", "clear store for key=$key; current keys=${maps.keys}")
	}
}
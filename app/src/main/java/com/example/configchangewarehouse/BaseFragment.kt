package com.example.configchangewarehouse

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlin.random.Random

abstract class BaseFragment : Fragment(R.layout.fragment_layout) {
	protected var key = -1

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		key = ConfigChangeWarehouse.attach(this, savedInstanceState)
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		ConfigChangeWarehouse.onSaveInstanceState(outState, key)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		view.setBackgroundColor(
			Color.rgb(
				Random.nextInt(255),
				Random.nextInt(255),
				Random.nextInt(255)
			)
		)
	}

	override fun onDestroy() {
		super.onDestroy()
		ConfigChangeWarehouse.checkCleanUp(this, key)
	}

	protected inline fun <reified T> getOrStore(crossinline initializer: () -> T) =
		ConfigChangeWarehouse.getOrStore(key, initializer)

	protected inline fun <reified T> getOrStoreTagged(tag: String, crossinline initializer: () -> T) =
		ConfigChangeWarehouse.getOrStoreTagged(tag, key, initializer)
}
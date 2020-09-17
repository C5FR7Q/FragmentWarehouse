package com.example.configchangewarehouse

import android.os.Bundle
import android.util.Log
import kotlin.random.Random

class FragmentC : BaseFragment() {
	companion object {
		var A = 0
		val CHAR_POOL: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
	}

	private var a: Int = 0
	private var b: String = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		A = (A + 1) % 2
		when (A) {
			0 -> {
				a = getOrStore { Random.nextInt(1000) }
				b = getOrStore { "" }
			}
			else -> {
				a = getOrStore { 0 }
				b = getOrStore {
					(1..Random.nextInt(10, 20))
						.map { Random.nextInt(0, CHAR_POOL.size) }
						.map(CHAR_POOL::get)
						.joinToString("")
				}
			}
		}
	}

	override fun onResume() {
		super.onResume()
		Log.d("###", "key=$key, class=${javaClass.simpleName} hash=${hashCode()}, a=$a, b=$b")
	}

}
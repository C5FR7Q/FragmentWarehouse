package com.example.fragmentwarehouse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
	companion object {
		var A = 0
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		main_button.setOnClickListener {
			A = (A + 1) % 3
			val f = when (A) {
				0 -> FragmentA()
				1 -> FragmentB()
				else -> FragmentC()
			}
			supportFragmentManager.beginTransaction().apply {
				val tag = f.javaClass.simpleName
				replace(R.id.main_container, f, tag)
				addToBackStack(tag)
				commit()
			}
		}
	}
}
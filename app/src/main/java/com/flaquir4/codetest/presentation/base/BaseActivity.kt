package com.flaquir4.codetest.presentation.base

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import kotlinx.android.synthetic.main.activity_login.*

open class BaseActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		addGestureNavigationSupport()
	}

	private fun addGestureNavigationSupport() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			setUiVisibility()
			container?.addSystemWindowInsetToPadding(top = true)

			var flags: Int = window.decorView.systemUiVisibility
			flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
			window.decorView.systemUiVisibility = flags
		}
	}

	private fun setUiVisibility() {
		window.decorView.systemUiVisibility =
			View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
			View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
			View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
	}

	private fun View.addSystemWindowInsetToPadding(
	    left: Boolean = false,
	    top: Boolean = true,
	    right: Boolean = false,
	    bottom: Boolean = false
	) {
		val (initialLeft, initialTop, initialRight, initialBottom) =
			listOf(paddingLeft, paddingTop, paddingRight, paddingBottom)

		ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
			view.updatePadding(
				left = initialLeft + (if (left) insets.systemWindowInsetLeft else 0),
				top = initialTop + (if (top) insets.systemWindowInsetTop else 0),
				right = initialRight + (if (right) insets.systemWindowInsetRight else 0),
				bottom = initialBottom + (if (bottom) insets.systemWindowInsetBottom else 0)
			)

			insets
		}
	}
}

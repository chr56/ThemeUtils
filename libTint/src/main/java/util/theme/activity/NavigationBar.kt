@file:Suppress("unused")

package util.theme.activity

import android.app.Activity
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.view.WindowInsetsControllerCompat
import util.theme.color.isColorLight

/**
 * @param color navigation bar background color
 * @param stubViewId stub view of statusbar if you have any
 */
fun Activity.setNavigationBarColor(@ColorInt color: Int, stubViewId: Int = 0) {
    with(window) {
        window.navigationBarColor = color
        if (stubViewId != 0) {
            decorView.rootView.findViewById<View>(stubViewId)?.setBackgroundColor(color)
        }
    }
    adjustNavigationBarButtons(color)
}

fun Activity.adjustNavigationBarButtons(bgColor: Int) = adjustNavigationBarButtons(isColorLight(bgColor))

fun Activity.adjustNavigationBarButtons(enabled: Boolean) {
    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = enabled
}
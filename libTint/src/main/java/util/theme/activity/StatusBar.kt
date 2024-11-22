@file:Suppress("unused")

package util.theme.activity

import android.app.Activity
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.view.WindowInsetsControllerCompat
import util.theme.color.isColorLight

/**
 * @param color statusbar background color
 * @param stubViewId stub view of statusbar if you have any
 */
fun Activity.setStatusbarColor(@ColorInt color: Int, stubViewId: Int = 0) {
    with(window) {
        statusBarColor = color
        if (stubViewId != 0) {
            decorView.rootView.findViewById<View>(stubViewId)?.setBackgroundColor(color)
        }
    }
    adjustStatusbarText(color)
}

fun Activity.adjustStatusbarText(backgroundColor: Int) = adjustStatusbarText(isColorLight(backgroundColor))

fun Activity.adjustStatusbarText(enabled: Boolean) {
    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = enabled
}
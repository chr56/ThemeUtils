@file:Suppress("unused")
package util.theme.activity

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.annotation.ColorInt
import util.theme.color.isColorLight

/**
 * @param color statusbar background color
 * @param stubViewId stub view of statusbar if you have any
 */
fun Activity.setStatusbarColor(@ColorInt color: Int, stubViewId: Int = 0) {
    with(this.window) {
        statusBarColor = color
        if (stubViewId != 0) {
            decorView.rootView.findViewById<View>(stubViewId)?.setBackgroundColor(color)
        }
    }
    adjustStatusbarText(color)
}

fun Activity.adjustStatusbarText(backgroundColor: Int) =
    adjustStatusbarText(isColorLight(backgroundColor))

fun Activity.adjustStatusbarText(enabled: Boolean) {
    @Suppress("DEPRECATION")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val wic = this.window.insetsController
        if (wic != null) {
            if (enabled) {
                wic.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                wic.setSystemBarsAppearance(
                    0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        }
    } else {
        this.window.decorView.run {
            val old = systemUiVisibility
            systemUiVisibility =
                if (enabled) {
                    old or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    old and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                }
        }
    }
}
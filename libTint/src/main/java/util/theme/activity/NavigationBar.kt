@file:Suppress("unused")
package util.theme.activity

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.annotation.ColorInt
import util.theme.color.isColorLight

/**
 * @param color NavigationBar background color
 */
fun Activity.setNavigationBarColor(@ColorInt color: Int) {
    this.window.navigationBarColor = color
    adjustNavigationBarButtons(color)
}

fun Activity.adjustNavigationBarButtons(bgColor: Int) {
    adjustNavigationBarButtons(isColorLight(bgColor))
}

fun Activity.adjustNavigationBarButtons(enabled: Boolean) {
    @Suppress("DEPRECATION")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        this.window.insetsController?.let { insetsController ->
            if (enabled) {
                insetsController.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                )
            } else {
                insetsController.setSystemBarsAppearance(
                    0,
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                )
            }
        }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.window.decorView.run {
            val visibility = if (enabled) {
                systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
            }
            systemUiVisibility = visibility
        }
    }
}
package util.theme.color

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import util.theme.resource.resolveColor

fun Context.isNightMode(): Boolean =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        resources.configuration.isNightModeActive
    } else {
        Configuration.UI_MODE_NIGHT_YES == (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK)
    }

fun Context.isWindowBackgroundDark(): Boolean {
    return !isColorLight(resolveColor(android.R.attr.windowBackground, 0))
}
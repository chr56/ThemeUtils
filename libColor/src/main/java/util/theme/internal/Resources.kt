@file:JvmName("ResourcesUtil")

package util.theme.internal

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes


@ColorInt
internal fun themeBackgroundColor(context: Context): Int =
    context.resolveThemeColor(android.R.attr.windowBackground, Color.WHITE)

@CheckResult
@ColorInt
internal fun Context.resolveThemeColor(@AttrRes attr: Int, @ColorInt fallbackColor: Int): Int {
    val a = theme.obtainStyledAttributes(intArrayOf(attr))
    return try {
        a.getColor(0, fallbackColor)
    } finally {
        a.recycle()
    }
}

@CheckResult
@ColorInt
internal fun Context.resolveThemeColorOrColorRes(@AttrRes attr: Int, @ColorRes colorRes: Int): Int {
    val a = theme.obtainStyledAttributes(intArrayOf(attr))
    return try {
        a.getColor(0, getColor(colorRes))
    } finally {
        a.recycle()
    }
}

internal fun Context.resolveThemeAttr(@AttrRes attr: Int, @AttrRes fallbackAttr: Int): Int {
    val value = TypedValue()
    theme.resolveAttribute(attr, value, true)
    return if (value.resourceId != 0) {
        attr
    } else {
        fallbackAttr
    }
}

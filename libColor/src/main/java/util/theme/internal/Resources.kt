@file:JvmName("ResourcesUtil")

package util.theme.internal

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes


fun Context.resolveColor(@AttrRes attr: Int, fallback: Int): Int {
    val a = theme.obtainStyledAttributes(intArrayOf(attr))
    return try {
        a.getColor(0, fallback)
    } finally {
        a.recycle()
    }
}

fun Context.resolveAttr(@AttrRes attr: Int, @AttrRes fallbackAttr: Int): Int {
    val value = TypedValue()
    theme.resolveAttribute(attr, value, true)
    return if (value.resourceId != 0) {
        attr
    } else {
        fallbackAttr
    }
}

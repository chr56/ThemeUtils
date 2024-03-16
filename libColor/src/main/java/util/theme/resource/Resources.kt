@file:JvmName("ResourcesUtil")

package util.theme.resource

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.TypedValue
import androidx.annotation.AttrRes
import util.theme.color.isColorLight




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

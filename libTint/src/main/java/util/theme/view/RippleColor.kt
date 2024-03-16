@file:JvmName("RippleColor") // ktlint-disable filename

package util.theme.view

import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import util.theme.tint.R

@ColorInt
internal fun defaultRippleColor(context: Context, useDarkRipple: Boolean): Int {
    // Light ripple is actually translucent black, and vice versa
    return ContextCompat.getColor(
        context,
        if (useDarkRipple) R.color.ripple_material_light else R.color.ripple_material_dark
    )
}

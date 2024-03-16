@file:JvmName("ColorStateList")

package util.theme.color

import android.content.res.ColorStateList
import androidx.annotation.ColorInt

fun selectableColorStateList(
    @ColorInt normalColor: Int,
    @ColorInt selectedColor: Int
): ColorStateList = ColorStateList(
    arrayOf(
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_checked)
    ),
    intArrayOf(
        normalColor,
        selectedColor
    )
)

fun disabledColorStateList(
    @ColorInt normal: Int,
    @ColorInt disabled: Int
): ColorStateList =
    ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(android.R.attr.state_enabled)
        ),
        intArrayOf(
            disabled,
            normal
        )
    )

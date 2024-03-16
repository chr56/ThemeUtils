@file:JvmName("DrawableUtil") // ktlint-disable filename

package util.theme.internal

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import util.theme.color.adjustAlpha
import util.theme.color.shiftColor
import util.theme.color.stripAlpha
import util.theme.drawable.createTintedDrawable
import util.theme.tint.R

internal fun modifySwitchDrawable(
    context: Context,
    from: Drawable,
    @ColorInt tintColor: Int,
    thumb: Boolean,
    compatSwitch: Boolean,
    useDarker: Boolean
): Drawable {
    val tint =
        if (useDarker) {
            shiftColor(tintColor, 1.1f)
        } else {
            tintColor
        }.apply {
            adjustAlpha(this, if (compatSwitch && !thumb) 0.5f else 1.0f)
        }
    val disabled: Int =
        if (thumb) {
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.switch_thumb_disabled_dark else R.color.switch_thumb_disabled_light
            )
        } else {
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.switch_track_disabled_dark else R.color.switch_track_disabled_light
            )
        }
    val normal: Int =
        if (thumb) {
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.switch_thumb_normal_dark else R.color.switch_thumb_normal_light
            )
        } else {
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.switch_track_normal_dark else R.color.switch_track_normal_light
            )
        }.apply {
            if (!compatSwitch) {
                stripAlpha(this)
            }
        }
    val colorStateList = ColorStateList(
        arrayOf(
            intArrayOf(
                -android.R.attr.state_enabled
            ),
            intArrayOf(
                android.R.attr.state_enabled,
                -android.R.attr.state_activated,
                -android.R.attr.state_checked
            ),
            intArrayOf(
                android.R.attr.state_enabled,
                android.R.attr.state_activated
            ),
            intArrayOf(
                android.R.attr.state_enabled,
                android.R.attr.state_checked
            )
        ),
        intArrayOf(
            disabled,
            normal,
            tint,
            tint
        )
    )
    return createTintedDrawable(from, colorStateList)
}

@file:JvmName("RadioButtonUtil")

package util.theme.view.radiobutton

import android.content.res.ColorStateList
import android.widget.RadioButton
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import util.theme.color.stripAlpha
import util.theme.tint.R

fun RadioButton.setTint(@ColorInt color: Int, useDarker: Boolean) {
    val sl = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(
                android.R.attr.state_enabled,
                -android.R.attr.state_checked
            ),
            intArrayOf(
                android.R.attr.state_enabled,
                android.R.attr.state_checked
            )
        ),
        intArrayOf(
            // Radio button includes own alpha for disabled state
            stripAlpha(
                ContextCompat.getColor(
                    context,
                    if (useDarker) R.color.control_disabled_dark else R.color.control_disabled_light
                )
            ),
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.control_normal_dark else R.color.control_normal_light
            ),
            color
        )
    )
    buttonTintList = sl
}

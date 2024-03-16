@file:JvmName("CheckBoxUtil")

package util.theme.viewtint

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.widget.CheckBox
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import util.theme.drawable.createTintedDrawable
import util.theme.tint.R

fun CheckBox.setTint(@ColorInt color: Int, useDarker: Boolean) {
    val colorStateList = ColorStateList(
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
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.control_disabled_dark else R.color.control_disabled_light
            ),
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.control_normal_dark else R.color.control_normal_light
            ),
            color
        )
    )
    @SuppressLint("ObsoleteSdkInt")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        buttonTintList = colorStateList
    } else {
        buttonDrawable =
            createTintedDrawable(
                ContextCompat.getDrawable(
                    context, androidx.appcompat.R.drawable.abc_btn_check_material
                )!!,
                colorStateList
            )
    }
}

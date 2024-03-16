@file:JvmName("SeekBarUtil")

package util.theme.view

import android.annotation.SuppressLint
import android.os.Build
import android.widget.SeekBar
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import util.theme.color.disabledColorStateList
import util.theme.tint.R

fun SeekBar.setTint(@ColorInt color: Int, useDarker: Boolean) {
    val colorStateList =
        disabledColorStateList(
            color,
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.control_disabled_dark else R.color.control_disabled_light
            )
        )
    @SuppressLint("ObsoleteSdkInt")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        thumbTintList = colorStateList
        progressTintList = colorStateList
    } else {
        val colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                color,
                BlendModeCompat.SRC_IN
            )
        if (indeterminateDrawable != null) {
            indeterminateDrawable.colorFilter = colorFilter
        }
        if (progressDrawable != null) {
            progressDrawable.colorFilter = colorFilter
        }
    }
}

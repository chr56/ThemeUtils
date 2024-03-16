@file:JvmName("ProgressBarUtil")

package util.theme.viewtint

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat

fun setTint(progressBar: ProgressBar, @ColorInt color: Int) {
    setTint(progressBar, color, false)
}

@JvmName("setTintKt")
fun ProgressBar.setTint(@ColorInt color: Int, skipIndeterminate: Boolean = false) =
    setTint(this, color, skipIndeterminate)

fun setTint(
    progressBar: ProgressBar,
    @ColorInt color: Int,
    skipIndeterminate: Boolean
) {
    val colorStateList = ColorStateList.valueOf(color)

    @SuppressLint("ObsoleteSdkInt")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        progressBar.progressTintList = colorStateList
        progressBar.secondaryProgressTintList = colorStateList
        if (!skipIndeterminate) {
            progressBar.indeterminateTintList = colorStateList
        }
    } else {
        val colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            color,
            BlendModeCompat.SRC_IN
        )
        progressBar.progressDrawable?.colorFilter = colorFilter
        if (!skipIndeterminate) {
            progressBar.indeterminateDrawable?.colorFilter = colorFilter
        }
    }
}

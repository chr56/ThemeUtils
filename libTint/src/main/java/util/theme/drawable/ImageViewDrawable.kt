@file:JvmName("ImageViewUtil")

package util.theme.drawable

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ImageView
import androidx.annotation.ColorInt

fun ImageView.setDrawableColor(@ColorInt color: Int) {
    @SuppressLint("ObsoleteSdkInt")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    } else {
        if (drawable != null) {
            setImageDrawable(
                createTintedDrawable(drawable!!, color)
            )
        }
    }
}

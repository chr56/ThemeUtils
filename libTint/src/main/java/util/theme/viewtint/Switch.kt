@file:JvmName("SwitchUtil") // ktlint-disable filename

package util.theme.viewtint

import android.annotation.SuppressLint
import android.widget.Switch
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SwitchCompat
import util.theme.internal.modifySwitchDrawable

@SuppressLint("UseSwitchCompatOrMaterialCode")
fun Switch.setTint(@ColorInt color: Int, useDarker: Boolean) {
    if (trackDrawable != null) {
        trackDrawable = modifySwitchDrawable(
            context = context,
            from = trackDrawable,
            tintColor = color,
            thumb = false,
            compatSwitch = false,
            useDarker = useDarker
        )
    }
    if (thumbDrawable != null) {
        thumbDrawable = modifySwitchDrawable(
            context = context,
            from = thumbDrawable,
            tintColor = color,
            thumb = true,
            compatSwitch = false,
            useDarker = useDarker
        )
    }
}

fun SwitchCompat.setTint(@ColorInt color: Int, useDarker: Boolean) {
    if (trackDrawable != null) {
        trackDrawable = modifySwitchDrawable(
            context = context,
            from = trackDrawable,
            tintColor = color,
            thumb = false,
            compatSwitch = true,
            useDarker = useDarker
        )
    }
    if (thumbDrawable != null) {
        thumbDrawable = modifySwitchDrawable(
            context = context,
            from = thumbDrawable,
            tintColor = color,
            thumb = true,
            compatSwitch = true,
            useDarker = useDarker
        )
    }
}

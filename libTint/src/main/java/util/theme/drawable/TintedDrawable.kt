package util.theme.drawable

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

@CheckResult
fun createTintedDrawable(source: Drawable, colorStateList: ColorStateList): Drawable =
    DrawableCompat.wrap(source.mutate()).apply {
        DrawableCompat.setTintList(this, colorStateList)
    }

@CheckResult
fun createTintedDrawable(source: Drawable, @ColorInt color: Int): Drawable =
    DrawableCompat.wrap(source.mutate()).apply {
        DrawableCompat.setTintMode(this, PorterDuff.Mode.SRC_IN)
        DrawableCompat.setTint(this, color)
    }
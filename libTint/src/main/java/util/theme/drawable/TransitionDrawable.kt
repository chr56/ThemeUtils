@file:JvmName("DrawableUtil")

package util.theme.drawable

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import androidx.annotation.ColorInt


fun createTransitionDrawable(
    @ColorInt startColor: Int,
    @ColorInt endColor: Int
): TransitionDrawable = createTransitionDrawable(ColorDrawable(startColor), ColorDrawable(endColor))

fun createTransitionDrawable(
    start: Drawable?,
    end: Drawable?
): TransitionDrawable = TransitionDrawable(arrayOf(start, end))


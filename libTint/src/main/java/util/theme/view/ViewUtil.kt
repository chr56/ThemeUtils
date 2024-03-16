/**
 * @author Karim Abou Zeid (kabouzeid)
 */
@file:JvmName("ViewUtil")

package util.theme.view

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import util.theme.drawable.createTransitionDrawable

fun View.removeOnGlobalLayoutListener(listener: OnGlobalLayoutListener?) {
    viewTreeObserver.removeOnGlobalLayoutListener(listener)
}

fun View.setBackgroundCompat(drawable: Drawable?) {
    ViewCompat.setBackground(this, drawable)
}

fun View.setBackgroundTransition(newDrawable: Drawable): TransitionDrawable {
    val transition = createTransitionDrawable(background, newDrawable)
    setBackgroundCompat(transition)
    return transition
}

fun View.setBackgroundColorTransition(@ColorInt newColor: Int): TransitionDrawable {
    val oldColor = background
    val start = oldColor ?: ColorDrawable(solidColor)
    val end: Drawable = ColorDrawable(newColor)
    val transition = createTransitionDrawable(start, end)
    setBackgroundCompat(transition)
    return transition
}

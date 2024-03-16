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

fun setBackgroundTransition(view: View, newDrawable: Drawable): TransitionDrawable {
    val transition = createTransitionDrawable(view.background, newDrawable)
    view.setBackgroundCompat(transition)
    return transition
}

fun setBackgroundColorTransition(view: View, @ColorInt newColor: Int): TransitionDrawable {
    val oldColor = view.background
    val start = oldColor ?: ColorDrawable(view.solidColor)
    val end: Drawable = ColorDrawable(newColor)
    val transition = createTransitionDrawable(start, end)
    view.setBackgroundCompat(transition)
    return transition
}

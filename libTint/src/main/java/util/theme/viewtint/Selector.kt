@file:JvmName("SelectorUtil")

package util.theme.viewtint

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import util.theme.color.disabledColorStateList
import util.theme.color.isColorLight
import util.theme.color.primaryTextColor
import util.theme.color.shiftColor
import util.theme.drawable.createTintedDrawable
import util.theme.internal.defaultRippleColor
import util.theme.tint.R
import util.theme.view.setBackgroundCompat

fun setTintSelector(view: View, @ColorInt color: Int, darker: Boolean, useDarkTheme: Boolean) {
    val isColorLight = isColorLight(color)
    val disabled =
        ContextCompat.getColor(
            view.context,
            if (useDarkTheme) R.color.button_disabled_dark else R.color.button_disabled_light
        )
    val pressed = shiftColor(color, if (darker) 0.9f else 1.1f)
    val activated = shiftColor(color, if (darker) 1.1f else 0.9f)
    val rippleColor = defaultRippleColor(view.context, isColorLight)
    val textColor = view.context.primaryTextColor(useDarkTheme)
    val sl: ColorStateList

    when (view) {
        is Button -> {
            sl = disabledColorStateList(color, disabled)
            @SuppressLint("ObsoleteSdkInt")
            if (view.getBackground() is RippleDrawable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val rd = view.getBackground() as RippleDrawable
                rd.setColor(ColorStateList.valueOf(rippleColor))
            }

            // Disabled text color state for buttons, may get overridden later by ATE tags
            view.setTextColor(
                disabledColorStateList(
                    textColor,
                    ContextCompat.getColor(
                        view.getContext(),
                        if (useDarkTheme) R.color.button_text_disabled_dark else R.color.button_text_disabled_light
                    )
                )
            )
        }

        is FloatingActionButton -> {
            // FloatingActionButton doesn't support disabled state?
            sl = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_pressed),
                    intArrayOf(android.R.attr.state_pressed)
                ),
                intArrayOf(
                    color,
                    pressed
                )
            )
            view.rippleColor = rippleColor
            view.backgroundTintList = sl
            if (view.drawable != null) view.setImageDrawable(
                createTintedDrawable(view.drawable, textColor)
            )
            return
        }

        else -> {
            sl = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_enabled),
                    intArrayOf(android.R.attr.state_enabled),
                    intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed),
                    intArrayOf(android.R.attr.state_enabled, android.R.attr.state_activated),
                    intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked)
                ),
                intArrayOf(
                    disabled,
                    color,
                    pressed,
                    activated,
                    activated
                )
            )
        }
    }
    var drawable = view.background
    if (drawable != null) {
        drawable = createTintedDrawable(drawable, sl)
        view.setBackgroundCompat(drawable)
    }
    if (view is TextView && view !is Button) {
        view.setTextColor(
            disabledColorStateList(
                textColor,
                view.context.primaryTextColor(useDarkTheme)
            )
        )
    }
}

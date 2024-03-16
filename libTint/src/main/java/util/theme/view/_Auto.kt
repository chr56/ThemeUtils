@file:JvmName("Auto")
@file:Suppress("unused")

/**
 * @author afollestad, plusCubed
 */
package util.theme.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.view.View
import android.widget.*
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import util.theme.color.adjustAlpha
import util.theme.color.disabledColorStateList
import util.theme.color.isColorLight
import util.theme.color.isWindowBackgroundDark
import util.theme.color.primaryTextColor
import util.theme.color.shiftColor
import util.theme.drawable.createTintedDrawable
import util.theme.drawable.setDrawableColor
import util.theme.tint.R
import util.theme.view.checkbox.setTint
import util.theme.view.edittext.setTint
import util.theme.view.progressbar.setTint
import util.theme.view.radiobutton.setTint
import util.theme.view.seekbar.setTint
import util.theme.view.switch.setTint

/**
 * @param tintBackground need to tint the background of a view or not
 * @param isDark isWindowBackgroundDark
 */
@JvmOverloads
fun View.tint(
    @ColorInt color: Int,
    tintBackground: Boolean = false,
    isDark: Boolean = context.isWindowBackgroundDark()
) {
    if (!tintBackground) {
        var tintRipples = true
        when (this) {
            is RadioButton -> this.setTint(color, isDark)
            is SeekBar -> this.setTint(color, isDark)
            is ProgressBar -> this.setTint(color, isDark)
            is EditText -> this.setTint(color, isDark)
            is CheckBox -> this.setTint(color, isDark)
            is ImageView -> this.setDrawableColor(color)
            is Switch -> this.setTint(color, isDark)
            is SwitchCompat -> this.setTint(color, isDark)
            else -> {
                // no ripples
                tintRipples = false
            }
        }

        @SuppressLint("ObsoleteSdkInt")
        // Ripples for the above views (e.g. when you tap and hold a switch or checkbox)
        if (background is RippleDrawable && tintRipples && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val unchecked =
                ContextCompat.getColor(context, if (isDark) R.color.ripple_material_dark else R.color.ripple_material_light)
            val checked = adjustAlpha(color, 0.4f)
            val colorStateList =
                ColorStateList(
                    arrayOf(
                        intArrayOf(
                            -android.R.attr.state_activated,
                            -android.R.attr.state_checked
                        ),
                        intArrayOf(android.R.attr.state_activated),
                        intArrayOf(android.R.attr.state_checked)
                    ),
                    intArrayOf(
                        unchecked,
                        checked,
                        checked
                    )
                )
            (background as RippleDrawable).setColor(colorStateList)
        }
    }
    if (tintBackground) {
        if (this is FloatingActionButton || this is Button) {
            setTintSelector(this, color, false, isDark)
        } else if (this.background != null) {
            var drawable = this.background
            if (drawable != null) {
                drawable = createTintedDrawable(drawable, color)
                setBackgroundCompat(drawable)
            }
        }
    }
}

fun View.setBackgroundTint(@ColorInt color: Int) = this.tint(color, true)

fun setTintSelector(view: View, @ColorInt color: Int, darker: Boolean, useDarkTheme: Boolean) {
    val isColorLight = isColorLight(color)
    val disabled =
        ContextCompat.getColor(
            view.context, if (useDarkTheme) R.color.button_disabled_dark else R.color.button_disabled_light
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

@ColorInt
internal fun defaultRippleColor(context: Context, useDarkRipple: Boolean): Int {
    // Light ripple is actually translucent black, and vice versa
    return ContextCompat.getColor(
        context,
        if (useDarkRipple) R.color.ripple_material_light else R.color.ripple_material_dark
    )
}

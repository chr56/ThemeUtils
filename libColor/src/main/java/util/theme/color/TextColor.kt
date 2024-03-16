/**
 * @author Karim Abou Zeid (kabouzeid)
 */
@file:JvmName("TextColor")

package util.theme.color

import android.content.Context
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat


/**
 * @param darkmode true if in dark theme (like dark background)
 */
@CheckResult
@ColorInt
fun Context.primaryTextColor(darkmode: Boolean): Int =
    ContextCompat.getColor(
        this,
        if (darkmode) R.color.primary_text_default_darkmode
        else R.color.primary_text_default_lightmode
    )

/**
 * @param darkmode true if in dark theme (like dark background)
 */
@CheckResult
@ColorInt
fun Context.secondaryTextColor(darkmode: Boolean): Int =
    ContextCompat.getColor(
        this,
        if (darkmode) R.color.secondary_text_default_darkmode
        else R.color.secondary_text_default_lightmode
    )

/**
 * @param darkmode true if in dark theme (like dark background)
 */
@CheckResult
@ColorInt
fun Context.primaryDisabledTextColor(darkmode: Boolean): Int =
    ContextCompat.getColor(
        this,
        if (darkmode) R.color.primary_text_disabled_darkmode
        else R.color.primary_text_disabled_lightmode
    )

/**
 * @param darkmode true if in dark theme (like dark background)
 */
@CheckResult
@ColorInt
fun Context.secondaryDisabledTextColor(darkmode: Boolean): Int =
    ContextCompat.getColor(
        this,
        if (darkmode) R.color.secondary_text_disabled_darkmode
        else R.color.secondary_text_disabled_lightmode
    )

/**
 * make contrasted text color with background color
 */
@CheckResult
@ColorInt
fun Context.primaryTextColor(@ColorInt backgroundColor: Int): Int =
    primaryTextColor(!isColorLight(backgroundColor))

/**
 * make contrasted text color with background color
 */
@CheckResult
@ColorInt
fun Context.secondaryTextColor(@ColorInt backgroundColor: Int): Int =
    secondaryTextColor(!isColorLight(backgroundColor))

/**
 * make contrasted text color with background color
 */
@CheckResult
@ColorInt
fun Context.primaryDisabledTextColor(@ColorInt backgroundColor: Int): Int =
    primaryDisabledTextColor(!isColorLight(backgroundColor))

/**
 * make contrasted text color with background color
 */
@CheckResult
@ColorInt
fun Context.secondaryDisabledTextColor(@ColorInt backgroundColor: Int): Int =
    secondaryDisabledTextColor(!isColorLight(backgroundColor))

/**
 * via `-night` resource set
 */
@CheckResult
@ColorInt
fun Context.primaryTextColor(): Int =
    ContextCompat.getColor(
        this,
        R.color.primary_text_default
    )

/**
 * via `-night` resource set
 */
@CheckResult
@ColorInt
fun Context.secondaryTextColor(): Int =
    ContextCompat.getColor(
        this,
        R.color.secondary_text_default
    )

/**
 * via `-night` resource set
 */
@CheckResult
@ColorInt
fun Context.primaryDisabledTextColor(): Int =
    ContextCompat.getColor(
        this,
        R.color.primary_text_disabled
    )

/**
 * via `-night` resource set
 */
@CheckResult
@ColorInt
fun Context.secondaryDisabledTextColor(): Int =
    ContextCompat.getColor(
        this,
        R.color.secondary_text_disabled
    )

@file:JvmName("ToolbarColor")

package util.theme.color

import android.content.Context
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt

@CheckResult
@ColorInt
fun toolbarIconColor(context: Context, @ColorInt toolbarBackgroundColor: Int): Int {
    return if (isColorLight(toolbarBackgroundColor)) {
        toolbarSubtitleColor(context, toolbarBackgroundColor)
    } else {
        toolbarTitleColor(context, toolbarBackgroundColor)
    }
}

@CheckResult
@ColorInt
fun toolbarSubtitleColor(context: Context, @ColorInt toolbarBackgroundColor: Int) =
    context.primaryTextColor(toolbarBackgroundColor)

@CheckResult
@ColorInt
fun toolbarTitleColor(context: Context, @ColorInt toolbarBackgroundColor: Int) =
    context.primaryTextColor(toolbarBackgroundColor)

@CheckResult
@ColorInt
@JvmName("toolbarIconColorKt")
fun Context.toolbarIconColor(@ColorInt toolbarBackgroundColor: Int): Int =
    toolbarIconColor(this, toolbarBackgroundColor)

@CheckResult
@ColorInt
@JvmName("toolbarSubtitleColorKt")
fun Context.toolbarSubtitleColor(@ColorInt toolbarBackgroundColor: Int): Int =
    toolbarSubtitleColor(this, toolbarBackgroundColor)

@CheckResult
@ColorInt
@JvmName("toolbarTitleColorKt")
fun Context.toolbarTitleColor(@ColorInt toolbarBackgroundColor: Int): Int =
    toolbarTitleColor(this, toolbarBackgroundColor)
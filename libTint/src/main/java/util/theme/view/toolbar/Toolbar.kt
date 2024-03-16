/**
 * @author Karim Abou Zeid (kabouzeid), chr_56
 */
@file:JvmName("ToolbarUtil")

package util.theme.view.toolbar

import android.content.Context
import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar
import util.theme.color.primaryTextColor
import util.theme.color.toolbarIconColor
import util.theme.color.toolbarSubtitleColor
import util.theme.color.toolbarTitleColor
import util.theme.drawable.createTintedDrawable
import util.theme.view.menu.setMenuColor

fun Context.setToolbarColor(toolbar: Toolbar, backgroundColor: Int) {
    toolbar.setBackgroundColor(backgroundColor)
    toolbar.setToolbarTextColor(this, backgroundColor)
    setMenuColor(this, toolbar, toolbar.menu, primaryTextColor(backgroundColor))
}

fun Toolbar.setToolbarTextColor(
    @ColorInt iconColor: Int,
    @ColorInt titleTextColor: Int,
    @ColorInt subtitleTextColor: Int
) {
    setTitleTextColor(titleTextColor)
    setSubtitleTextColor(subtitleTextColor)
    tintNavigationIcon(iconColor)
}

fun Toolbar.setToolbarTextColor(
    context: Context,
    @ColorInt toolbarBackgroundColor: Int
) = setToolbarTextColor(
    toolbarIconColor(context, toolbarBackgroundColor),
    toolbarTitleColor(context, toolbarBackgroundColor),
    toolbarSubtitleColor(context, toolbarBackgroundColor)
)


fun Toolbar.tintNavigationIcon(@ColorInt color: Int) {
    navigationIcon?.let {
        navigationIcon = createTintedDrawable(it, color)
    }
}


fun Toolbar.tintCollapseIcon(@ColorInt color: Int) {
    collapseIcon?.let {
        collapseIcon = createTintedDrawable(it, color)
    }
}
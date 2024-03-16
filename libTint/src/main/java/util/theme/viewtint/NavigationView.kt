/**
 * @author Karim Abou Zeid (kabouzeid)
 */
@file:JvmName("NavigationViewUtil")

package util.theme.viewtint

import androidx.annotation.ColorInt
import com.google.android.material.navigation.NavigationView
import util.theme.color.selectableColorStateList

fun NavigationView.setItemIconColors(
    @ColorInt normalColor: Int,
    @ColorInt selectedColor: Int
) {
    itemIconTintList = selectableColorStateList(normalColor, selectedColor)
}

fun NavigationView.setItemTextColors(
    @ColorInt normalColor: Int,
    @ColorInt selectedColor: Int
) {
    itemTextColor = selectableColorStateList(normalColor, selectedColor)
}

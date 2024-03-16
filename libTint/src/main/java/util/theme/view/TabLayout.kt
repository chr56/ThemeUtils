@file:JvmName("TabLayoutUtil") // ktlint-disable filename

package util.theme.view

import androidx.annotation.ColorInt
import com.google.android.material.tabs.TabLayout
import util.theme.color.selectableColorStateList
import util.theme.drawable.createTintedDrawable

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
fun TabLayout?.setTabIconColors(
    @ColorInt normalColor: Int,
    @ColorInt selectedColor: Int
) {
    if (this != null) {
        val colorStateList = selectableColorStateList(normalColor, selectedColor)
        for (i in 0 until tabCount) {
            val tab = this.getTabAt(i)
            if (tab != null && tab.icon != null) {
                tab.icon = createTintedDrawable(tab.icon!!, colorStateList)
            }
        }
    }
}

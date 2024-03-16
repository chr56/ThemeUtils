/**
 * @author Karim Abou Zeid (kabouzeid)
 */
@file:JvmName("ActivityColor")

package util.theme.activity

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import util.theme.viewtint.setMenuColor
import util.theme.viewtint.setToolbarTextColor
import util.theme.color.primaryTextColor


fun Activity.setActivityToolbarColor(toolbar: Toolbar, backgroundColor: Int) {
    toolbar.setBackgroundColor(backgroundColor)
    setToolbarTextColor(this, toolbar, backgroundColor)
    setMenuColor(this, toolbar, toolbar.menu, primaryTextColor(backgroundColor))
}



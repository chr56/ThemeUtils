@file:Suppress("unused")

package util.theme.activity

import android.app.Activity
import android.app.ActivityManager
import android.os.Build
import androidx.annotation.ColorInt
import util.theme.color.stripAlpha

fun Activity.setTaskDescriptionColor(@ColorInt color: Int) {
    // Task description requires fully opaque color
    val fullColor = stripAlpha(color)
    // Sets color of entry in the system recents page
    setTaskDescription(
        if (Build.VERSION.SDK_INT >= 33) {
            ActivityManager.TaskDescription.Builder()
                .setBackgroundColor(fullColor).setLabel(this.title as String)
                .build()
        } else {
            @Suppress("DEPRECATION")
            (ActivityManager.TaskDescription(this.title as String, null, fullColor))
        }
    )
}
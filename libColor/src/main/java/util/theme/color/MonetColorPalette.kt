package util.theme.color

import android.content.Context
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi

@JvmInline
value class MonetColorPalette(val value: Int) {
    constructor(@MonetColor.Type type: Int, @MonetColor.Depth depth: Int) : this((type shl SHIFT) + depth)

    val type: Int @MonetColor.Type get() = value ushr SHIFT
    val depth: Int @MonetColor.Depth get() = value shl SHIFT ushr SHIFT

    @RequiresApi(Build.VERSION_CODES.S)
    @ColorInt
    fun color(context: Context): Int = MonetColor.dynasticColor(context, type, depth)

    companion object {
        private const val SHIFT = 16


        val defaultMonetPrimaryColor get() = MonetColorPalette(MonetColor.ACCENT1, MonetColor.DEPTH_400)
        val defaultMonetAccentColor get() = MonetColorPalette(MonetColor.ACCENT1, MonetColor.DEPTH_700)
    }
}
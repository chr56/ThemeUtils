@file:JvmName("SearchViewTintUtil")

package util.theme.view.searchview

import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorInt
import util.theme.color.adjustAlpha
import util.theme.internal.reflectDeclaredField
import util.theme.view.edittext.setCursorTint
import util.theme.drawable.setDrawableColor
import android.widget.SearchView as SearchViewOS
import androidx.appcompat.widget.SearchView as SearchViewX

fun setSearchViewContentColor(searchView: SearchViewOS?, @ColorInt color: Int) {
    searchView?.apply {
        tintImageView("mSearchButton", color)
        tintImageView("mGoButton", color)
        tintImageView("mCloseButton", color)
        tintImageView("mVoiceButton", color)
        tintTextView(color)
    }
}

fun setSearchViewContentColor(searchView: SearchViewX?, @ColorInt color: Int) {
    searchView?.apply {
        tintImageView("mSearchButton", color)
        tintImageView("mGoButton", color)
        tintImageView("mCloseButton", color)
        tintImageView("mVoiceButton", color)
        tintTextView(color)
    }
}

internal fun SearchViewOS.tintTextView(@ColorInt color: Int) =
    try {
        tintEditTextView(this, color)
    } catch (e: Exception) {
        Log.w(TAG, "Reflect EditText failed: ")
        Log.w(TAG, e)
    }

internal fun SearchViewX.tintTextView(@ColorInt color: Int) =
    try {
        tintEditTextView(this, color)
    } catch (e: Exception) {
        Log.w(TAG, "Reflect EditText failed: ")
        Log.w(TAG, e)
    }

private inline fun <reified V> tintEditTextView(view: V, @ColorInt color: Int) {
    val editText: EditText = view.reflectDeclaredField("mSearchSrcTextView")
    editText.setTextColor(color)
    editText.setHintTextColor(adjustAlpha(color, 0.5f))
    setCursorTint(editText, color)
}


internal fun SearchViewOS.tintImageView(fieldName: String, @ColorInt color: Int) =
    try {
        tintDrawableImageView(this, fieldName, color)
    } catch (e: Exception) {
        Log.w(TAG, "Reflect $fieldName failed: ")
        Log.w(TAG, e)
    }

internal fun SearchViewX.tintImageView(fieldName: String, @ColorInt color: Int) =
    try {
        tintDrawableImageView(this, fieldName, color)
    } catch (e: Exception) {
        Log.w(TAG, "Reflect $fieldName failed: ")
        Log.w(TAG, e)
    }

private inline fun <reified V> tintDrawableImageView(
    view: V,
    fieldName: String,
    @ColorInt color: Int
) {
    val imageView: ImageView = view.reflectDeclaredField(fieldName)
    imageView.setDrawableColor(color)
}

private const val TAG = "ReflectSearchView"
/**
 * @author Karim Abou Zeid (kabouzeid), chr_56
 */
@file:JvmName("MenuUtil")

package util.theme.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.annotation.ColorInt
import androidx.appcompat.view.menu.*
import androidx.appcompat.widget.ActionMenuView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import util.theme.color.isColorLight
import util.theme.drawable.createTintedDrawable
import util.theme.internal.declaredField
import util.theme.internal.reflectDeclaredField
import util.theme.internal.resolveColor
import android.widget.SearchView as SearchViewOS
import androidx.appcompat.widget.SearchView as SearchViewX

/**
 * tint the menu
 *
 * @param context         context of toolbar's container
 * @param toolbar         menu's container
 * @param menu            the menu to tint if using not toolbar's menu
 * @param menuWidgetColor menu icon color
 */
@SuppressLint("RestrictedApi")
fun setMenuColor(
    context: Context,
    toolbar: Toolbar,
    menu: Menu?,
    @ColorInt menuWidgetColor: Int
) {
    val actualMenu: Menu? = menu ?: toolbar.menu
    tintMenuActionIcons(toolbar, actualMenu, menuWidgetColor)
    applyOverflowMenuTint(context, toolbar, menuWidgetColor)
    if (context is Activity) {
        context.setOverflowButtonColor(menuWidgetColor)
    }
    try {
        // Tint immediate overflow menu items

        val currentPresenterCb: MenuPresenter.Callback =
            toolbar.reflectDeclaredField("mActionMenuPresenterCallback")

        if (currentPresenterCb !is mMenuPresenterCallback) {
            val newPresenterCb =
                mMenuPresenterCallback(context, menuWidgetColor, currentPresenterCb, toolbar)

            val currentMenuCb: MenuBuilder.Callback =
                toolbar.reflectDeclaredField("mMenuBuilderCallback")
            toolbar.setMenuCallbacks(newPresenterCb, currentMenuCb)

            val menuView: ActionMenuView? =
                toolbar.reflectDeclaredField("mMenuView")
            menuView?.setMenuCallbacks(newPresenterCb, currentMenuCb)
        }

        // OnMenuItemClickListener to tint submenu items
        val currentClickListener: Toolbar.OnMenuItemClickListener =
            toolbar.reflectDeclaredField("mOnMenuItemClickListener")

        if (currentClickListener !is mOnMenuItemClickListener) {
            val newClickListener =
                mOnMenuItemClickListener(context, menuWidgetColor, currentClickListener, toolbar)
            toolbar.setOnMenuItemClickListener(newClickListener)
        }
    } catch (e: Exception) {
        Log.v(REFLECT_TAG, e.message.orEmpty())
    }
}


/**
 * tint `CollapseIcon`, all `Icon` menu items, `SearchView`
 */
fun tintMenuActionIcons(toolbar: Toolbar, menu: Menu?, @ColorInt iconColor: Int) {
    toolbar.tintCollapseIcon(iconColor)
    if (menu != null && menu.size() > 0) {
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            if (item.icon != null) {
                item.icon = createTintedDrawable(item.icon!!, iconColor)
            }
            // Search view theming
            item.actionView?.let { v ->
                when (v) {
                    is SearchViewOS -> {
                        setSearchViewContentColor(item.actionView as SearchViewOS, iconColor)
                    }

                    is SearchViewX -> {
                        setSearchViewContentColor(item.actionView as SearchViewX, iconColor)
                    }
                }
            }
        }
    }
}

fun applyOverflowMenuTint(context: Context, toolbar: Toolbar?, @ColorInt color: Int) {
    toolbar?.post {
        try {
            val actionMenuView: ActionMenuView =
                toolbar.reflectDeclaredField("mMenuView")

            // Actually ActionMenuPresenter //todo
            val presenter: BaseMenuPresenter = /* : ActionMenuPresenter = */
                actionMenuView.reflectDeclaredField("mPresenter")

            val overflowMenuPopupHelper: MenuPopupHelper =
                presenter.reflectDeclaredField("mOverflowPopup")
            setTintForMenuPopupHelper(context, overflowMenuPopupHelper, color)

            val subMenuPopupHelper: MenuPopupHelper =
                presenter.reflectDeclaredField("mActionButtonPopup")
            setTintForMenuPopupHelper(context, subMenuPopupHelper, color)
        } catch (e: Exception) {
            Log.v(REFLECT_TAG, e.message.orEmpty())
        }
    }
}

fun setTintForMenuPopupHelper(
    context: Context,
    menuPopupHelper: MenuPopupHelper?,
    @ColorInt color: Int
) {
    menuPopupHelper?.let {
        try {
            val listView = (it.popup as? ShowableListMenu)?.listView
            listView?.viewTreeObserver?.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                @SuppressLint("ObsoleteSdkInt")
                override fun onGlobalLayout() {
                    try {
                        val checkboxField =
                            ListMenuItemView::class.java.declaredField("mCheckBox")

                        val radioButtonField =
                            ListMenuItemView::class.java.declaredField("mRadioButton")

                        val isDark =
                            !isColorLight(context.resolveColor(android.R.attr.windowBackground, 0))

                        for (i in 0 until listView.childCount) {
                            val v = listView.getChildAt(i) as? ListMenuItemView ?: continue

                            (checkboxField[v] as? CheckBox)?.let { check ->
                                check.setTint(color, isDark)
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    check.background = null
                                }
                            }
                            (radioButtonField[v] as? RadioButton)?.let { radioButton ->
                                radioButton.setTint(color, isDark)
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    radioButton.background = null
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.v(REFLECT_TAG, e.message.orEmpty())
                    }
                    listView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        } catch (e: Exception) {
            Log.v(REFLECT_TAG, e.message.orEmpty())
        }
    }
}

internal fun Activity.setOverflowButtonColor(@ColorInt color: Int) {
    (window.decorView as ViewGroup).also {
        it.viewTreeObserver
            .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val outViews = ArrayList<View>()
                    it.findViewsWithText(
                        outViews,
                        getString(androidx.appcompat.R.string.abc_action_menu_overflow_description),
                        View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION
                    )
                    if (outViews.isNotEmpty()) {
                        val overflow = outViews[0] as AppCompatImageView
                        overflow.setImageDrawable(createTintedDrawable(overflow.drawable, color))
                        it.removeOnGlobalLayoutListener(this)
                    }
                }
            })
    }
}

internal class mOnMenuItemClickListener(
    private val mContext: Context,
    @param:ColorInt private val mColor: Int,
    private val mParentListener: Toolbar.OnMenuItemClickListener?,
    private val mToolbar: Toolbar
) : Toolbar.OnMenuItemClickListener {

    override fun onMenuItemClick(item: MenuItem): Boolean {
        applyOverflowMenuTint(mContext, mToolbar, mColor)
        return mParentListener != null && mParentListener.onMenuItemClick(item)
    }
}

@SuppressLint("RestrictedApi")
internal class mMenuPresenterCallback(
    private val mContext: Context,
    @param:ColorInt private val mColor: Int,
    private val mParentCb: MenuPresenter.Callback?,
    private val mToolbar: Toolbar
) : MenuPresenter.Callback {

    override fun onCloseMenu(menu: MenuBuilder, allMenusAreClosing: Boolean) {
        mParentCb?.onCloseMenu(menu, allMenusAreClosing)
    }

    override fun onOpenSubMenu(subMenu: MenuBuilder): Boolean {
        applyOverflowMenuTint(mContext, mToolbar, mColor)
        return mParentCb != null && mParentCb.onOpenSubMenu(subMenu)
    }
}

private const val REFLECT_TAG = "ReflectMenu"

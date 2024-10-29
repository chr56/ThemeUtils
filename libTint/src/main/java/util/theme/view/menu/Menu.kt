/**
 * @author Karim Abou Zeid (kabouzeid), chr_56
 */
@file:JvmName("MenuUtil")

package util.theme.view.menu

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.annotation.ColorInt
import androidx.annotation.MainThread
import androidx.appcompat.view.menu.*
import androidx.appcompat.widget.ActionMenuPresenter
import androidx.appcompat.widget.ActionMenuView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import util.theme.color.isWindowBackgroundDark
import util.theme.drawable.createTintedDrawable
import util.theme.internal.declaredField
import util.theme.internal.reflectDeclaredField
import util.theme.view.checkbox.setTint
import util.theme.view.radiobutton.setTint
import util.theme.view.removeOnGlobalLayoutListener
import util.theme.view.searchview.setSearchViewContentColor
import util.theme.view.toolbar.tintCollapseIcon
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
    toolbar.post {
        tintToolbarOverflowMenu(context, toolbar, menuWidgetColor)
    }
    if (context is Activity) {
        context.setOverflowButtonColor(menuWidgetColor)
    }
    try {
        // Tint immediate overflow menu items

        val currentPresenterCb: MenuPresenter.Callback? =
            toolbar.reflectDeclaredField("mActionMenuPresenterCallback")

        if (currentPresenterCb != null && currentPresenterCb !is mMenuPresenterCallback) {
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
        val currentClickListener: Toolbar.OnMenuItemClickListener? =
            toolbar.reflectDeclaredField("mOnMenuItemClickListener")

        if (currentClickListener != null && currentClickListener !is mOnMenuItemClickListener) {
            val newClickListener =
                mOnMenuItemClickListener(context, menuWidgetColor, currentClickListener, toolbar)
            toolbar.setOnMenuItemClickListener(newClickListener)
        }
    } catch (e: Throwable) {
        Log.v(REFLECT_TAG, "Failed to change menu color: ${e.javaClass.simpleName} ${e.message}")
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

@MainThread
@SuppressLint("RestrictedApi")
fun tintToolbarOverflowMenu(context: Context, toolbar: Toolbar, @ColorInt color: Int) {

    val presenter: ActionMenuPresenter =
        try {
            val actionMenuView: ActionMenuView =
                toolbar.reflectDeclaredField("mMenuView")

            val presenter: ActionMenuPresenter =
                actionMenuView.reflectDeclaredField("mPresenter")

            presenter
        } catch (e: Throwable) {
            Log.v(REFLECT_TAG, "Failed to obtain MenuPresenter: ${e.javaClass.simpleName} ${e.message}")
            return
        }


    try {
        val overflowMenuPopupHelper: ActionMenuPresenter.OverflowPopup? = presenter.reflectDeclaredField("mOverflowPopup")
        overflowMenuPopupHelper?.tintMenuItems(context, color)
    } catch (e: Throwable) {
        Log.v(REFLECT_TAG, "Failed to apply OverflowPopup Tint: ${e.javaClass.simpleName} ${e.message}")
    }

    try {
        val subMenuPopupHelper: ActionMenuPresenter.ActionButtonSubmenu? = presenter.reflectDeclaredField("mActionButtonPopup")
        subMenuPopupHelper?.tintMenuItems(context, color)
    } catch (e: Throwable) {
        Log.v(REFLECT_TAG, "Failed to apply ActionButtonSubmenu Tint: ${e.javaClass.simpleName} ${e.message}")
    }
}

@Suppress("INACCESSIBLE_TYPE")
@SuppressLint("RestrictedApi")
fun MenuPopupHelper.tintMenuItems(
    context: Context,
    @ColorInt color: Int
) {
    try {
        val listView = (popup as? ShowableListMenu)?.listView
        listView?.viewTreeObserver?.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            @SuppressLint("ObsoleteSdkInt")
            override fun onGlobalLayout() {
                try {
                    val checkboxField =
                        ListMenuItemView::class.java.declaredField("mCheckBox")

                    val radioButtonField =
                        ListMenuItemView::class.java.declaredField("mRadioButton")

                    val isDark = context.isWindowBackgroundDark()

                    for (i in 0 until listView.childCount) {
                        val v = listView.getChildAt(i) as? ListMenuItemView ?: continue

                        (checkboxField[v] as? CheckBox)?.let { checkBox ->
                            checkBox.setTint(color, isDark)
                            if (SDK_INT >= LOLLIPOP) checkBox.background = null
                        }
                        (radioButtonField[v] as? RadioButton)?.let { radioButton ->
                            radioButton.setTint(color, isDark)
                            if (SDK_INT >= LOLLIPOP) radioButton.background = null
                        }
                    }
                } catch (e: Throwable) {
                    Log.v(REFLECT_TAG, "Failed to tint Menu Items at onGlobalLayout:")
                    Log.v(REFLECT_TAG, "${e.javaClass.simpleName} ${e.message}")
                }
                listView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    } catch (e: Throwable) {
        Log.v(REFLECT_TAG, "Failed to tint Menu Items: ${e.javaClass.simpleName} ${e.message}")
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
        mToolbar.post {
            tintToolbarOverflowMenu(mContext, mToolbar, mColor)
        }
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
        mToolbar.post {
            tintToolbarOverflowMenu(mContext, mToolbar, mColor)
        }
        return mParentCb != null && mParentCb.onOpenSubMenu(subMenu)
    }
}

private const val REFLECT_TAG = "ReflectMenu"

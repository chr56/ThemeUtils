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
import util.theme.view.toolbar.tintNavigationIcon
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
    context: Context, toolbar: Toolbar, menu: Menu?, @ColorInt menuWidgetColor: Int
) {

    toolbar.post {
        tintToolbarBackIcon(toolbar, menuWidgetColor)
        tintToolbarMenuActionIcons(menu ?: toolbar.menu, menuWidgetColor)
    }

    tintOverflowButtonColor(context, menuWidgetColor)

    tintOverflowMenuItems(toolbar, menuWidgetColor)

    applyMenuPresenterCallback(toolbar, menuWidgetColor)

    applyMenuItemClickListener(toolbar, menuWidgetColor)
}

/**
 * tint `CollapseIcon` & `NavigationIcon` (Icon on the left side)
 */
fun tintToolbarBackIcon(toolbar: Toolbar, @ColorInt iconColor: Int) {
    toolbar.tintCollapseIcon(iconColor)
    toolbar.tintNavigationIcon(iconColor)
}

/**
 * tint all `SHOW_AS_ACTION` menu items & `SearchView`
 */
fun tintToolbarMenuActionIcons(menu: Menu?, @ColorInt iconColor: Int) {
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

/**
 * tint
 */
@SuppressLint("RestrictedApi")
fun tintOverflowMenuItems(toolbar: Toolbar, @ColorInt iconColor: Int) {

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
        overflowMenuPopupHelper?.tintMenuItems(toolbar.context, iconColor)
    } catch (e: Throwable) {
        Log.v(REFLECT_TAG, "Failed to apply OverflowPopup Tint: ${e.javaClass.simpleName} ${e.message}")
    }

    try {
        val subMenuPopupHelper: ActionMenuPresenter.ActionButtonSubmenu? = presenter.reflectDeclaredField("mActionButtonPopup")
        subMenuPopupHelper?.tintMenuItems(toolbar.context, iconColor)
    } catch (e: Throwable) {
        Log.v(REFLECT_TAG, "Failed to apply ActionButtonSubmenu Tint: ${e.javaClass.simpleName} ${e.message}")
    }
}

@SuppressLint("RestrictedApi")
fun MenuPopupHelper.tintMenuItems(context: Context, @ColorInt menuWidgetColor: Int) {
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
                            checkBox.setTint(menuWidgetColor, isDark)
                            if (SDK_INT >= LOLLIPOP) checkBox.background = null
                        }
                        (radioButtonField[v] as? RadioButton)?.let { radioButton ->
                            radioButton.setTint(menuWidgetColor, isDark)
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

/**
 * tint Overflow Menu Button ("3 dots")
 */
fun tintOverflowButtonColor(context: Context, @ColorInt iconColor: Int) {
    if (context is Activity) {
        (context.window.decorView as ViewGroup).also {
            it.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val outViews = ArrayList<View>()
                    it.findViewsWithText(
                        outViews,
                        context.getString(androidx.appcompat.R.string.abc_action_menu_overflow_description),
                        View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION
                    )
                    if (outViews.isNotEmpty()) {
                        val overflow = outViews[0] as AppCompatImageView
                        overflow.setImageDrawable(createTintedDrawable(overflow.drawable, iconColor))
                        it.removeOnGlobalLayoutListener(this)
                    }
                }
            })
        }
    }
}

/**
 * register a delegated [Toolbar.OnMenuItemClickListener] for updating Overflow Menu Items
 */
fun applyMenuItemClickListener(toolbar: Toolbar, @ColorInt menuWidgetColor: Int) {
    // OnMenuItemClickListener to tint submenu items
    try {
        val itemClickListener: Toolbar.OnMenuItemClickListener? = toolbar.reflectDeclaredField("mOnMenuItemClickListener")

        if (itemClickListener != null && itemClickListener !is DelegateOnMenuItemClickListener) {
            val delegateItemClickListener = DelegateOnMenuItemClickListener(itemClickListener, toolbar, menuWidgetColor)
            toolbar.setOnMenuItemClickListener(delegateItemClickListener)
        }
    } catch (e: Throwable) {
        Log.v(REFLECT_TAG, "Failed to change menu color: ${e.javaClass.simpleName} ${e.message}")
    }
}

internal class DelegateOnMenuItemClickListener(
    private val parent: Toolbar.OnMenuItemClickListener?,
    private val toolbar: Toolbar,
    @param:ColorInt private val mColor: Int,
) : Toolbar.OnMenuItemClickListener {

    override fun onMenuItemClick(item: MenuItem): Boolean {
        toolbar.post {
            tintOverflowMenuItems(toolbar, mColor)
        }
        return parent != null && parent.onMenuItemClick(item)
    }
}

/**
 * register a delegated [MenuPresenter.Callback] for updating Overflow Menu Items
 */
@SuppressLint("RestrictedApi")
fun applyMenuPresenterCallback(toolbar: Toolbar, @ColorInt menuWidgetColor: Int) {
    // Tint immediate overflow menu items
    try {
        val presenterCallback: MenuPresenter.Callback? = toolbar.reflectDeclaredField("mActionMenuPresenterCallback")

        if (presenterCallback != null && presenterCallback !is DelegateMenuPresenterCallback) {
            val delegatePresenterCallback = DelegateMenuPresenterCallback(presenterCallback, toolbar, menuWidgetColor)
            val menuBuilderCallback: MenuBuilder.Callback = toolbar.reflectDeclaredField("mMenuBuilderCallback")

            toolbar.setMenuCallbacks(delegatePresenterCallback, menuBuilderCallback)

            val menuView: ActionMenuView? = toolbar.reflectDeclaredField("mMenuView")
            menuView?.setMenuCallbacks(delegatePresenterCallback, menuBuilderCallback)
        }
    } catch (e: Throwable) {
        Log.v(REFLECT_TAG, "Failed to change menu color: ${e.javaClass.simpleName} ${e.message}")
    }
}

@SuppressLint("RestrictedApi")
internal class DelegateMenuPresenterCallback(
    private val parent: MenuPresenter.Callback?,
    private val toolbar: Toolbar,
    @param:ColorInt private val color: Int,
) : MenuPresenter.Callback {

    override fun onCloseMenu(menu: MenuBuilder, allMenusAreClosing: Boolean) {
        parent?.onCloseMenu(menu, allMenusAreClosing)
    }

    override fun onOpenSubMenu(subMenu: MenuBuilder): Boolean {
        toolbar.post {
            tintOverflowMenuItems(toolbar, color)
        }
        return parent != null && parent.onOpenSubMenu(subMenu)
    }
}

private const val REFLECT_TAG = "ReflectMenu"

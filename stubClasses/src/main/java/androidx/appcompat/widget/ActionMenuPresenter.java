package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcelable;
import android.view.View;

import androidx.appcompat.view.menu.BaseMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.view.ActionProvider;

/**
 * Stub class for reflection
 */
@SuppressLint("RestrictedApi")
public class ActionMenuPresenter extends BaseMenuPresenter implements ActionProvider.SubUiVisibilityListener {

    OverflowPopup mOverflowPopup;
    ActionButtonSubmenu mActionButtonPopup;

    public ActionMenuPresenter(Context context) {
        super(context, 0, 0);
    }

    @Override
    public void bindItemView(MenuItemImpl item, MenuView.ItemView itemView) {
        throw new RuntimeException("Stub Class");
    }

    @Override
    public Parcelable onSaveInstanceState() {
        throw new RuntimeException("Stub Class");
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        throw new RuntimeException("Stub Class");
    }

    @Override
    public void onSubUiVisibilityChanged(boolean isVisible) {
        throw new RuntimeException("Stub Class");
    }

    /**
     * @noinspection InnerClassMayBeStatic
     */
    public class ActionButtonSubmenu extends MenuPopupHelper {
        public ActionButtonSubmenu(Context context, SubMenuBuilder subMenu, View anchorView) {
            super(context, subMenu, anchorView, false, 0);
        }
    }

    /**
     * @noinspection InnerClassMayBeStatic
     */
    public class OverflowPopup extends MenuPopupHelper {
        public OverflowPopup(Context context, MenuBuilder menu, View anchorView, boolean overflowOnly) {
            super(context, menu, anchorView, overflowOnly, 0);
        }
    }
}

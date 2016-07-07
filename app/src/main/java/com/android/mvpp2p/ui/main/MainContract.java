package com.android.mvpp2p.ui.main;


import android.app.Fragment;
import android.view.MenuItem;

import com.android.mvpp2p.base.BasePresenter;
import com.android.mvpp2p.base.BaseView;

/**
 * Created by apple on 16/7/5.
 */
public interface MainContract {
    interface View extends BaseView {
        void setTitle(CharSequence title);
        void closeDrawers();
        void showFragment(Fragment fragment);
        void intentSeting();

    }

    interface Presenter extends BasePresenter<View> {
        void exits();
        void NavigationItemSelected(MenuItem item);
    }
}

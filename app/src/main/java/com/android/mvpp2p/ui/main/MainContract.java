package com.android.mvpp2p.ui.main;

import com.android.mvpp2p.ui.BasePresenter;
import com.android.mvpp2p.ui.BaseView;

/**
 * Created by apple on 16/7/5.
 */
public interface MainContract {
    interface View extends BaseView {
        void setTitle(CharSequence title);
        void closeDrawers();

    }

    interface Presenter extends BasePresenter<View> {
        void exits();
    }
}

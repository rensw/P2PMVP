package com.android.mvpp2p.ui.information;


import com.android.mvpp2p.base.BasePresenter;
import com.android.mvpp2p.base.BaseView;

/**
 * Created by apple on 16/7/5.
 */
public interface InformationContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<View> {
    }
}

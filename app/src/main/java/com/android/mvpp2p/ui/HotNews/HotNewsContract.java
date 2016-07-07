package com.android.mvpp2p.ui.HotNews;


import com.android.mvpp2p.base.BasePresenter;
import com.android.mvpp2p.base.BaseView;
import com.android.mvpp2p.bean.NewsConsultData;

import java.util.List;

/**
 * Created by apple on 16/7/5.
 */
public interface HotNewsContract {
    interface View extends BaseView {
        void RenderList(List<NewsConsultData> managerList);

        void showLoading();

        void hideLoading();

        void onError();
    }

    interface Presenter extends BasePresenter<View> {
        void BindList(int tid);
    }
}

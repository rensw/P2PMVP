package com.android.mvpp2p.ui.product;


import com.android.mvpp2p.base.BasePresenter;
import com.android.mvpp2p.base.BaseView;
import com.android.mvpp2p.bean.ManagerListData;

import java.util.List;

/**
 * Created by apple on 16/7/5.
 */
public interface ProductContract {
    interface View extends BaseView {
        void ItemClick(int position);

        void RenderList(List<ManagerListData> managerList);

        void showLoading();

        void hideLoading();

        void onError();
    }

    interface Presenter extends BasePresenter<View> {
        void BindList();
    }
}

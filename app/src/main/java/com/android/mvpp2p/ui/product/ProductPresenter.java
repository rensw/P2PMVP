package com.android.mvpp2p.ui.product;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.android.mvpp2p.api.HttpApi;
import com.android.mvpp2p.bean.BaseData;
import com.android.mvpp2p.bean.ManagerListData;
import com.android.mvpp2p.injector.PerActivity;
import com.android.mvpp2p.utils.ToastUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by apple on 16/7/5.
 */
@PerActivity
public class ProductPresenter implements ProductContract.Presenter {

    Context mContext;
    HttpApi mHttpApi;
    ProductContract.View MainView;
    private Subscription mSubscription;

    @Inject
    public ProductPresenter(Context mContext, HttpApi mHttpApi) {
        this.mContext = mContext;
        this.mHttpApi = mHttpApi;
    }

    @Override
    public void attachView(@NonNull ProductContract.View view) {
        this.MainView = view;
    }

    @Override
    public void detachView() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        MainView = null;
    }

    @Override
    public void BindList() {
        //去获取首页数据
        MainView.showLoading();
        mSubscription = mHttpApi.getSubList().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseData>() {
            @Override
            public void call(BaseData baseData) {
                MainView.hideLoading();
                if (baseData != null) {
                    if ("0".equals(baseData.getState())) {
                        List<ManagerListData> managerListDatas = JSON.parseArray(baseData.getData(), ManagerListData.class);
                        MainView.RenderList(managerListDatas);
                    } else {
                        MainView.onError();
                        ToastUtil.showToast(baseData.getMsg());
                    }
                } else {
                    MainView.onError();
                    ToastUtil.showToast("加载失败");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
                MainView.onError();
                ToastUtil.showToast(throwable.getMessage());
            }
        });
    }
}

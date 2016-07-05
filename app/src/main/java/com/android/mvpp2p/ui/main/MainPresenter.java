package com.android.mvpp2p.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.android.mvpp2p.AppManager;
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
public class MainPresenter implements MainContract.Presenter {

    Context mContext;
    private MainContract.View mMainView;
    private Subscription mSubscription;
    private HttpApi mHttpApi;

    @Inject
    public MainPresenter(HttpApi mHttpApi, Context mContext) {
        this.mHttpApi = mHttpApi;
        this.mContext = mContext;
    }

    @Override
    public void attachView(@NonNull MainContract.View view) {
        mMainView = view;
        //去获取首页数据
        mSubscription = mHttpApi.getSubList().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseData>() {
            @Override
            public void call(BaseData baseData) {
                if (baseData != null) {
                    if ("0".equals(baseData.getState())) {
                        List<ManagerListData> managerListDatas = JSON.parseArray(baseData.getData(), ManagerListData.class);
                    } else {
                        ToastUtil.showToast(baseData.getMsg());
                    }
                } else {
                    ToastUtil.showToast("加载失败");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
                ToastUtil.showToast(throwable.getMessage());
            }
        });
    }

    @Override
    public void detachView() {

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mMainView = null;
    }

    @Override
    public void exits() {
        if (isCanExit()) {
            AppManager.getAppManager().AppExit(mContext);
        }
    }

    public long mExitTime = 0;

    public boolean isCanExit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            mExitTime = System.currentTimeMillis();
            ToastUtil.showToast("再按一次退出程序");
            return false;
        }
        return true;
    }
}

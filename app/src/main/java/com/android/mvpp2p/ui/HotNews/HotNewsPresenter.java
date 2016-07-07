package com.android.mvpp2p.ui.HotNews;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.android.mvpp2p.api.HttpApi;
import com.android.mvpp2p.bean.NewsConsultData;
import com.android.mvpp2p.injector.PerActivity;
import com.android.mvpp2p.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by apple on 16/7/5.
 */
@PerActivity
public class HotNewsPresenter implements HotNewsContract.Presenter {

    Context mContext;
    HttpApi mHttpApi;
    HotNewsContract.View MainView;
    private Subscription mSubscription;
    private int pageNum = 1;
    List<NewsConsultData> mNewList = new ArrayList<>();

    @Inject
    public HotNewsPresenter(Context mContext, HttpApi mHttpApi) {
        this.mContext = mContext;
        this.mHttpApi = mHttpApi;
    }


    @Override
    public void attachView(@NonNull HotNewsContract.View view) {
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
    public void BindList(int tid) {

        mSubscription = mHttpApi.getHotNewsList(tid, pageNum).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String result) {
                MainView.hideLoading();
                if (StringUtils.isNotEmpty(result)) {
                    if (pageNum == 1) {
                        mNewList.clear();
                    }
                    mNewList.addAll(JSON.parseArray(result, NewsConsultData.class));
                    MainView.RenderList(mNewList);
                } else {
                    if (pageNum > 1)
                        pageNum--;
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                MainView.onError();
                if (pageNum > 1)
                    pageNum--;
            }
        });
    }
}

package com.android.mvpp2p.ui.information;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.mvpp2p.api.HttpApi;
import com.android.mvpp2p.injector.PerActivity;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by apple on 16/7/5.
 */
@PerActivity
public class InformationPresenter implements InformationContract.Presenter {

    Context mContext;
    HttpApi mHttpApi;
    InformationContract.View MainView;
    private Subscription mSubscription;

    @Inject
    public InformationPresenter(Context mContext, HttpApi mHttpApi) {
        this.mContext = mContext;
        this.mHttpApi = mHttpApi;
    }


    @Override
    public void attachView(@NonNull InformationContract.View view) {
        this.MainView = view;
    }

    @Override
    public void detachView() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        MainView = null;
    }

}

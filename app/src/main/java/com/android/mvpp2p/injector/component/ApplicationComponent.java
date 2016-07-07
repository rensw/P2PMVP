package com.android.mvpp2p.injector.component;

import android.app.NotificationManager;
import android.content.Context;

import com.android.mvpp2p.MyApplication;
import com.android.mvpp2p.api.HttpApi;
import com.android.mvpp2p.base.BaseActivity;
import com.android.mvpp2p.injector.module.ApiModule;
import com.android.mvpp2p.injector.module.ApplicationModule;
import com.android.mvpp2p.injector.module.DBModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sll on 2016/3/8.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class, ApiModule.class, DBModule.class
})
public interface ApplicationComponent {

    Context getContext();

    HttpApi getHttpApi();

    NotificationManager getNotificationManager();

    void inject(MyApplication mApplication);

    void inject(BaseActivity mBaseActivity);

}

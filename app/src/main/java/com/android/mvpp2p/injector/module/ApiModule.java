package com.android.mvpp2p.injector.module;

import com.android.mvpp2p.api.HttpApi;
import com.android.mvpp2p.components.retrofit.RequestHelper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by sll on 2015/3/7.
 */
@Module public class ApiModule {

    @Provides
    @Singleton
    public HttpApi provideGameApi(RequestHelper requestHelper,
                                  @Named("api") OkHttpClient okHttpClient) {
        return new HttpApi(requestHelper, okHttpClient);
    }
}

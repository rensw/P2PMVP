package com.android.mvpp2p.api;

import com.android.mvpp2p.bean.BaseData;
import com.android.mvpp2p.components.retrofit.FastJsonConverterFactory;
import com.android.mvpp2p.components.retrofit.RequestHelper;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by apple on 16/7/5.
 */
public class HttpApi {

    static final String BASE_URL = "http://api.touyouquan.com/";

    private HttpService mHttpService;
    private RequestHelper mRequestHelper;
    public String APPNAME = "p2plicai";
    public String TIME = String.valueOf(System.currentTimeMillis());

    public HttpApi(RequestHelper mRequestHelper, OkHttpClient mOkHttpClient) {
        this.mRequestHelper = mRequestHelper;
        Retrofit retrofit =
                new Retrofit.Builder().addConverterFactory(FastJsonConverterFactory.create())
                        .client(mOkHttpClient)
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
        mHttpService = retrofit.create(HttpService.class);
    }

    /**
     * 获取首页列表信息
     */
    public Observable<BaseData> getSubList() {
        Map<String, String> params = new HashMap<>();
        params.put("app_name", APPNAME);
        params.put("current_time", TIME);
        return mHttpService.getSubList(params, mRequestHelper.ChangeMd5(params))
                .subscribeOn(Schedulers.io());
    }
}

package com.android.mvpp2p.api;

import com.android.mvpp2p.bean.BaseData;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by apple on 16/7/5.
 */
public interface HttpService {
    @FormUrlEncoded
    @POST("P2plicai/list")
    Observable<BaseData> getSubList(
            @FieldMap Map<String, String> params,@Header("token") String header);

    @GET("p2peye_api.php?mod=licai&ac=article")
    Observable<String> getHotNewsList(
            @QueryMap Map<String, String> params);
}

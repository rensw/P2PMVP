package com.android.mvpp2p.components.okhttp;

import com.android.mvpp2p.components.UserStorage;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sll on 2016/2/23.
 */
public class CookieInterceptor implements Interceptor {


  private UserStorage mUserStorage;

  public CookieInterceptor(UserStorage mUserStorage) {
    this.mUserStorage = mUserStorage;
  }

  @Override public Response intercept(Chain chain) throws IOException {
    Request original = chain.request();

    return chain.proceed(original);
  }
}

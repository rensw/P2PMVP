package com.android.mvpp2p.components;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

/**
 * Created by sll on 2015/7/11.
 */

public class UserStorage {

  private Context mContext;

  public UserStorage(Context mContext) {
    this.mContext = mContext;
  }

  private String cookie;
  private String token;


  private void removeCookie() {
    CookieSyncManager.createInstance(mContext);
    CookieManager cookieManager = CookieManager.getInstance();
    cookieManager.removeAllCookie();
    CookieSyncManager.getInstance().sync();
  }


  public void setCookie(String cookie) {
    this.cookie = cookie;
  }

  public void setToken(String token) {
    this.token = token;
  }
}

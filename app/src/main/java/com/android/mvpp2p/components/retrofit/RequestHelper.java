package com.android.mvpp2p.components.retrofit;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.android.mvpp2p.components.UserStorage;
import com.android.mvpp2p.utils.MD5Utils;
import com.android.mvpp2p.utils.SettingPrefUtil;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by gzsll on 2014/9/23 0023.
 */
public class RequestHelper {

  private Context mContext;
  protected UserStorage mUserStorage;

  public RequestHelper(Context mContext, UserStorage mUserStorage) {
    this.mContext = mContext;
    this.mUserStorage = mUserStorage;
  }

  public Map<String, String> getHttpRequestMap() {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("client", getDeviceId());
    map.put("night", SettingPrefUtil.getNightModel(mContext) ? "1" : "0");
    return map;
  }

  public String getAndroidId() {
    return Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
  }

  public String getDeviceId() {
    String deviceId;
    TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    if (tm.getDeviceId() == null) {
      deviceId = getAndroidId();
    } else {
      deviceId = tm.getDeviceId();
    }
    return deviceId;
  }

  /**
   * 两次MD5加密
   *
   * @param map 需要排序的map
   * @return
   */
  public String ChangeMd5(Map<String, String> map) {
    StringBuffer md5String = new StringBuffer();
    Map<String, String> sortmap = sortMapByKey(map);
    for (String key : sortmap.keySet()) {
      md5String.append(sortmap.get(key));
    }

    return MD5Utils.encode(MD5Utils.encode(md5String.toString()));
  }

  /**
   * 使用 Map按key进行排序
   *
   * @param map
   * @return
   */
  public  Map<String, String> sortMapByKey(Map<String, String> map) {
    if (map == null || map.isEmpty()) {
      return null;
    }

    Map<String, String> sortMap = new TreeMap<String, String>(
            new MapKeyComparator());

    sortMap.putAll(map);

    return sortMap;
  }

  class MapKeyComparator implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {
      return str1.compareTo(str2);
    }
  }

}

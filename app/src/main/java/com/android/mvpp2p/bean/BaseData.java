package com.android.mvpp2p.bean;

/**
 * Created by sll on 2015/8/22.
 */
public class BaseData {

  /**
   * uid : 4847679
   * status : 200
   * data :
   * msg : 发表成功
   */
  public String state;
  public String data;
  public String msg;

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}

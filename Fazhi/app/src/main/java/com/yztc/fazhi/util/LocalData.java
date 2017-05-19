package com.yztc.fazhi.util;

import com.yztc.fazhi.App;

/**
 * Sharedpreferences本地保存
 */

public class LocalData {
    public static String getSessionKey() {
        return (String) SPUtils.get(App.getApp(), "SessionKey","");
    }

    public static void putSessionKey(String session_key) {
        SPUtils.put(App.getApp(), "SessionKey",session_key);
    }

    public static void putUserID(int user_id) {
        SPUtils.put(App.getApp(), "user_id",user_id);
    }

    public static String getUserID() {
      return  (String) SPUtils.get(App.getApp(), "user_id","");
    }
}

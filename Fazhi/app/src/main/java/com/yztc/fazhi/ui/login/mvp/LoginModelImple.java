package com.yztc.fazhi.ui.login.mvp;

import com.yztc.fazhi.net.BaseResponse;
import com.yztc.fazhi.net.NetRequest;
import com.yztc.fazhi.ui.login.bean.UserBean;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;

public class LoginModelImple implements ILoginModel {

    @Override
    public Observable<BaseResponse<UserBean>> login(String username, String passwrod) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", passwrod);
        RequestBody requestBody = NetRequest.generateReqBody(map);
        return NetRequest.getInstance().getApi().userLogin(requestBody);
    }
}

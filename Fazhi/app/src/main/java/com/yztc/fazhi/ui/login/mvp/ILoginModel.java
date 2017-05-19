package com.yztc.fazhi.ui.login.mvp;

import com.yztc.fazhi.base.BaseModel;
import com.yztc.fazhi.net.BaseResponse;
import com.yztc.fazhi.ui.login.bean.UserBean;

import rx.Observable;

public interface ILoginModel extends BaseModel {
    //登陆
    Observable<BaseResponse<UserBean>> login(String username, String passwrod);
}

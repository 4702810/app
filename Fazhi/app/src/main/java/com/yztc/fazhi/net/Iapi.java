package com.yztc.fazhi.net;

import com.yztc.fazhi.ui.login.bean.UserBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface Iapi {
    @POST("userLogin")
    Observable<BaseResponse<UserBean>> userLogin(@Body RequestBody params);
}

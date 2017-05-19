package com.yztc.fazhi.ui.login.mvp;

import android.text.TextUtils;

import com.yztc.fazhi.base.BaseView;
import com.yztc.fazhi.net.BaseResponse;
import com.yztc.fazhi.net.NetRequest;
import com.yztc.fazhi.net.NetSubscriber;
import com.yztc.fazhi.net.RxHelper;
import com.yztc.fazhi.ui.login.bean.UserBean;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;

public class LoginPresenterImpl implements ILoginPresenter {

    private ILoginModel model;
    private ILoginView view;

    public LoginPresenterImpl(ILoginView view) {
        this.model = new LoginModelImple();
        this.view = view;
    }

    @Override
    public void login(String username, String passwrod) {
        if(TextUtils.isEmpty(username)){
            view.showError("账号不能为空");
            return;
        }
        if(TextUtils.isEmpty(passwrod)){
            view.showError("密码不能为空");
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", passwrod);
        RequestBody requestBody = NetRequest.generateReqBody(map);
        Observable<BaseResponse<UserBean>> observable = NetRequest.getInstance()
                                                                  .getApi()
                                                                  .userLogin(requestBody);
        observable.compose(RxHelper.schedulers())
                  .compose(RxHelper.transform())
                  .subscribe(new NetSubscriber<UserBean>() {
                      @Override
                      public BaseView getView() {
                          return view;
                      }

                      @Override
                      public void onNext(UserBean userBean) {
                          // 成功的回调
                     }
                  });
    }
}

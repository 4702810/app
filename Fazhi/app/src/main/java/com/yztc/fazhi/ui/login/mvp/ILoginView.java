package com.yztc.fazhi.ui.login.mvp;

import com.yztc.fazhi.base.BaseView;
import com.yztc.fazhi.ui.login.bean.UserBean;

/**
 * Created by wanggang on 2017/2/22.
 */

public interface ILoginView extends BaseView{
    void showHint(UserBean userBean);
}

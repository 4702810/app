package com.yztc.fazhi.net;

import com.yztc.fazhi.base.BaseView;

import rx.Subscriber;

public abstract class NetSubscriber<T> extends Subscriber<T> {

    private BaseView view;

    @Override
    public void onStart() {
        super.onStart();
        view = getView();
        view.showLoading();
    }

    @Override
    public void onCompleted() {
        view.hideLoading();
    }

    @Override
    public void onError(Throwable e) {
        view.hideLoading();

        ExceptionHandle.ResponeThrowable throwable;
        //后台逻辑执行失败，抛出的异常
        if(e instanceof ExceptionHandle.ResponeThrowable){
            throwable = (ExceptionHandle.ResponeThrowable) e;
        } else {
            //网络操作执行的异常
            throwable = ExceptionHandle.handleException(e);
        }
        view.showError(throwable.message);
    }

    public abstract BaseView getView();
}

package com.yztc.fazhi.net;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxHelper {

    // 设置请求的线程 重复代码 每次都要写所以封装起来
    public static Observable.Transformer schedulers() {
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable)observable)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    // 本方法为设置拦截器
    // 1.用了Map操作符 来拦截每一次的消息 过滤正确的放行给订阅者 失败的丢给错误码统一处理类
    // 2.onErrorResumeNext设置如果是请求上的错误 比如网络错误/连接超时之类的 直接丢给错误码统一处理类
    public static <T> Observable.Transformer transform() {
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable)observable)
                        .map(new TransformerFun())
                        .onErrorResumeNext(new ServerExceptionFun());
            }
        };
    }

    /**
     * 请求失败 将错误代码的回调转接给统一处理类
     * @param <T>
     */
    static class ServerExceptionFun<T> implements Func1<Throwable,Observable<T>>{

        @Override
        public Observable<T> call(Throwable throwable) {
            return Observable.error(ExceptionHandle.handleException(throwable));
        }
    }

    /**
     * 拦截器的具体逻辑
     * @param <T>
     */
    static class TransformerFun<T> implements Func1<BaseResponse<T>,T>{

        @Override
        public T call(BaseResponse<T> baseResponse) {
            if(baseResponse.is_success()){
                return baseResponse.getData();
            }
            ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
            exception.message = baseResponse.getError_content();
            throw exception;
        }
    }
}

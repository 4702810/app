package com.yztc.fazhi.net;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetRequest {

    private static final String TAG = "===NET REQUEST===";
    private static NetRequest instance;
    private OkHttpClient client;
    private Retrofit retrofit;
    private Iapi iapi;

    // 第一步 构造RequestBody请求体
    public static RequestBody generateReqBody(HashMap<String, Object> map){
        JSONObject params = new JSONObject();
        params.putAll(map);
        return RequestBody.create(NetConfig.TypeJSON, params.toJSONString());
    }

    // 第二步 构建单例模式
    public static NetRequest getInstance() {
        if (instance == null) {
            synchronized (NetRequest.class) {
                if (instance == null) {
                    instance = new NetRequest();
                }
            }
        }
        return instance;
    }

    // 第三步 1.获得单例模式时会初始化该类 初始化时就构造好OkHttp和Retrofit2个对象 并且设置好参数
    public NetRequest() {
        client = getClient();
        retrofit = getRetrofit();
    }

    // 第三步 2.创建OkHttp的Client对象
    private OkHttpClient getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(TAG, message);
            }
        });
        // 注意这个对象HttpLoggingInterceptor注意这里的拦截器 用来设置log的拦截等级的
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                               .addInterceptor(interceptor)
                               .connectTimeout(20, TimeUnit.SECONDS)
                               .readTimeout(20, TimeUnit.SECONDS)
                               .writeTimeout(20, TimeUnit.SECONDS)
                               .build();
    }

    // 第三步 3.创建Retrofit对象并且设置参数
    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                           .client(client)// 传入OKhttp的client对象
                           .addConverterFactory(GsonConverterFactory.create())
                           .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                           .baseUrl(NetConfig.BASE_URL)// 传入URL
                           .build();
    }

    // 第四步 通过retrofit创建Iapi的实现类对象(retrofit已经帮你实现好了 不需要自己去实现 只要调用方法)
    // 待会model要用来调你写好的Iapi里的接口 接口里定义好了方法为userLogin(放第一步获取的请求体)
    public Iapi getApi() {
        if (iapi == null) {
            iapi = retrofit.create(Iapi.class);
        }
        return iapi;
    }
}

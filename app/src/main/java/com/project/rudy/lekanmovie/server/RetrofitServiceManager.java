package com.project.rudy.lekanmovie.server;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by maoyan on 2018/5/3.
 */

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private Retrofit mRetrofit;

    public RetrofitServiceManager() {

        OkHttpClient.Builder okHttpBuider = new OkHttpClient.Builder();
        okHttpBuider.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        okHttpBuider.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        okHttpBuider.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .client(okHttpBuider.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(APIConfigs.API_MOVIE)
                .build();
    }

    private static class SingletonHolder {
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    /**
     * 获取RetrofitServiceManager
     *
     * @return
     */
    public static RetrofitServiceManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public <T> T createService(Class<T> service) {
        return mRetrofit.create(service);
    }
}

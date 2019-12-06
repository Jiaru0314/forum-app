package com.jit.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.jit.util.HttpUtil;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : XZQ
 * date   : 2019/12/3
 * description    :封装Retrofit
 */
public class RetrofitManager {
    private static RetrofitManager mRetrofitManager;
    private ApiService apiService;

    public static RetrofitManager getInstance() {
        if (null == mRetrofitManager) {
            synchronized (RetrofitManager.class) {
                if (null == mRetrofitManager) {
                    mRetrofitManager = new RetrofitManager();
                }
            }
        }
        return mRetrofitManager;
    }

    private RetrofitManager() {
        initRetrofit();
    }

    private void initRetrofit() {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));
        Gson gson = builder.create();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        apiService = mRetrofit.create(ApiService.class);
    }

    public ApiService getService() {
        return apiService;
    }

    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .addInterceptor(interceptor)
            .readTimeout(8, TimeUnit.SECONDS)
            .connectTimeout(8, TimeUnit.SECONDS)
//            .addNetworkInterceptor(new HttpNetWorkInterceptor())
//            .cache(cache)
            .build();

}

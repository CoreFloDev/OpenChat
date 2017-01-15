package io.coreflodev.openchat.common.network;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String BASE_URL = "http://192.168.1.22:4242";

    private Retrofit retrofit;

    public NetworkService(Context context) {
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
                new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        .registerTypeAdapterFactory(AutoValueGsonTypeAdapterFactory.create()).create());

        File httpCacheDirectory = new File(context.getCacheDir(), "httpCache");
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    try {
                        return chain.proceed(chain.request());
                    } catch (ConnectException e) {
                        Request offlineRequest = chain.request().newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24)
                                .build();
                        return chain.proceed(offlineRequest);
                    }
                })
                .build();

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .client(httpClient)
                .baseUrl(BASE_URL)
                .build();
    }

    public <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }
}

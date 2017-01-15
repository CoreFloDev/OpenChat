package io.coreflodev.openchat.common.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String BASE_URL = "http://192.168.1.22:4242";

    private Retrofit retrofit;

    public NetworkService(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .build();
    }

    public <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }
}

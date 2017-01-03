package io.coreflodev.httpcacheexample.common.service;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.ryanharter.auto.value.gson.AutoValueGsonAdapterFactoryProcessor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String BASE_URL = "http://192.168.1.18:4242";

    private Retrofit retrofit;

    public NetworkService() {
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
                new GsonBuilder().create());

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .baseUrl(BASE_URL)
                .build();
    }

    public <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }
}

package io.coreflodev.openchat.common.network;

import com.google.gson.GsonBuilder;

import retrofit2.converter.gson.GsonConverterFactory;

public class GsonService {

    private GsonConverterFactory gsonConverterFactory;

    public GsonService() {
        gsonConverterFactory = GsonConverterFactory.create(
                new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        .registerTypeAdapterFactory(GsonAutoValueTypeAdapterFactory.create())
                        .create());
    }

    public GsonConverterFactory getGsonConverterFactory() {
        return gsonConverterFactory;
    }
}

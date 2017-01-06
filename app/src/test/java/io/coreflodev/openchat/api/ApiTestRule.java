package io.coreflodev.openchat.api;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.coreflodev.openchat.common.network.AutoValueGsonTypeAdapterFactory;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiTestRule implements TestRule {

    private MockWebServer server;
    private Retrofit retrofit;

    public ApiTestRule() {
        server = new MockWebServer();
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                server.start();

                GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
                        new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                .registerTypeAdapterFactory(AutoValueGsonTypeAdapterFactory.create()).create());

                retrofit = new Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(gsonConverterFactory)
                        .baseUrl(server.url("").toString())
                        .build();

                base.evaluate();

                server.shutdown();

            }
        };
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

    public void enqueue(String body) {
        MockResponse response = new MockResponse();
        response.setBody(body);
        enqueue(response);
    }

    public void enqueue(MockResponse mockResponse) {
        server.enqueue(mockResponse);
    }
}

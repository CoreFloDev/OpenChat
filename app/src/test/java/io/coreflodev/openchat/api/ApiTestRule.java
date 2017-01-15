package io.coreflodev.openchat.api;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.File;

import io.coreflodev.openchat.common.network.GsonService;
import io.coreflodev.openchat.common.network.HttpService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApiTestRule implements TestRule {

    private Context context;

    private MockWebServer server;
    private Retrofit retrofit;

    public ApiTestRule() {
        server = new MockWebServer();
        context = mock(Context.class);
        when(context.getCacheDir()).thenReturn(new File(""));
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                server.start();

                retrofit = new Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(new GsonService().getGsonConverterFactory())
                        .baseUrl(server.url("").toString())
                        .client(new HttpService(context).getHttpClient())
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

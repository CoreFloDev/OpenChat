package io.coreflodev.openchat.common.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.coreflodev.openchat.api.ChatService;
import io.coreflodev.openchat.common.network.GsonService;
import io.coreflodev.openchat.common.network.HttpService;
import io.coreflodev.openchat.common.network.NetworkService;
import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    public NetworkService provideNetworkService(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
        return new NetworkService(okHttpClient, gsonConverterFactory);
    }

    @Provides
    @ApplicationScope
    public ChatService provideChatService(NetworkService networkService) {
        return networkService.createService(ChatService.class);
    }

    @Provides
    @ApplicationScope
    public OkHttpClient provideOkHttpClient() {
        return new HttpService(context).getHttpClient();
    }

    @Provides
    @ApplicationScope
    public GsonConverterFactory provideGsonConverterFactory() {
        return new GsonService().getGsonConverterFactory();
    }
}

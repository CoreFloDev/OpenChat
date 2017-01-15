package io.coreflodev.openchat.common.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.coreflodev.openchat.api.ChatService;
import io.coreflodev.openchat.common.network.NetworkService;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    public NetworkService provideNetworkService() {
        return new NetworkService(context);
    }

    @Provides
    @ApplicationScope
    public ChatService provideChatService(NetworkService networkService) {
        return networkService.createService(ChatService.class);
    }
}

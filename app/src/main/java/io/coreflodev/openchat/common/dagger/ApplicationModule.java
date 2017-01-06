package io.coreflodev.openchat.common.dagger;

import dagger.Module;
import dagger.Provides;
import io.coreflodev.openchat.api.ChatService;
import io.coreflodev.openchat.common.network.NetworkService;

@Module
public class ApplicationModule {

    @Provides
    @ApplicationScope
    public NetworkService provideNetworkService() {
        return new NetworkService();
    }

    @Provides
    @ApplicationScope
    public ChatService provideChatService(NetworkService networkService) {
        return networkService.createService(ChatService.class);
    }
}

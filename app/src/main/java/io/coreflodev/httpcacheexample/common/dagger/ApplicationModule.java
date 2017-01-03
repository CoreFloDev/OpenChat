package io.coreflodev.httpcacheexample.common.dagger;

import dagger.Module;
import dagger.Provides;
import io.coreflodev.httpcacheexample.api.ChatService;
import io.coreflodev.httpcacheexample.common.service.NetworkService;

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

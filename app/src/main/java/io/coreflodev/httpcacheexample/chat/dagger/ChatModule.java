package io.coreflodev.httpcacheexample.chat.dagger;

import dagger.Module;
import dagger.Provides;
import io.coreflodev.httpcacheexample.chat.ChatPresenter;

@Module
public class ChatModule {

    @Provides
    @ChatScope
    public ChatPresenter providesChatPresenter() {
        return new ChatPresenter();
    }
}

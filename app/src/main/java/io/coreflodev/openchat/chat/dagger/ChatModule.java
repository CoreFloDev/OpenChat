package io.coreflodev.openchat.chat.dagger;

import dagger.Module;
import dagger.Provides;
import io.coreflodev.openchat.api.ChatService;
import io.coreflodev.openchat.chat.ChatPresenter;

@Module
public class ChatModule {

    @Provides
    @ChatScope
    public ChatPresenter providesChatPresenter(ChatService chatService) {
        return new ChatPresenter(chatService);
    }
}

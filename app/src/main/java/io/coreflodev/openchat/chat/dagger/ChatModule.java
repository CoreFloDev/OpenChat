package io.coreflodev.openchat.chat.dagger;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import io.coreflodev.openchat.api.ChatService;
import io.coreflodev.openchat.chat.ChatPresenter;
import io.coreflodev.openchat.chat.repository.ChatRepository;
import io.coreflodev.openchat.chat.repository.ChatRepositoryImpl;

@Module
public class ChatModule {

    @Provides
    @ChatScope
    public ChatPresenter providesChatPresenter(ChatService chatService, ChatRepository chatRepository) {
        return new ChatPresenter(chatService, chatRepository);
    }

    @Provides
    @ChatScope
    public ChatRepository providesChatRepository(SharedPreferences sharedPreferences) {
        return new ChatRepositoryImpl(sharedPreferences);
    }
}

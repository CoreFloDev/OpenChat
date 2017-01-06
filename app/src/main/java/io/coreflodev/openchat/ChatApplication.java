package io.coreflodev.openchat;

import android.app.Application;

import io.coreflodev.openchat.chat.dagger.ChatComponent;
import io.coreflodev.openchat.chat.dagger.ChatModule;
import io.coreflodev.openchat.chat.dagger.DaggerChatComponent;
import io.coreflodev.openchat.common.dagger.ApplicationComponent;
import io.coreflodev.openchat.common.dagger.DaggerApplicationComponent;

public class ChatApplication extends Application {

    private ApplicationComponent applicationComponent;
    private ChatComponent chatComponent;

    public ChatApplication() {
        this.applicationComponent = DaggerApplicationComponent.create();
        this.chatComponent = DaggerChatComponent.builder()
                .applicationComponent(applicationComponent)
                .chatModule(new ChatModule())
                .build();
    }

    public ChatComponent getChatComponent() {
        return chatComponent;
    }
}

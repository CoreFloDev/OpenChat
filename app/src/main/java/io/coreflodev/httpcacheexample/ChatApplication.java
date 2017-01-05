package io.coreflodev.httpcacheexample;

import android.app.Application;

import io.coreflodev.httpcacheexample.chat.dagger.ChatComponent;
import io.coreflodev.httpcacheexample.chat.dagger.ChatModule;
import io.coreflodev.httpcacheexample.chat.dagger.DaggerChatComponent;
import io.coreflodev.httpcacheexample.common.dagger.ApplicationComponent;
import io.coreflodev.httpcacheexample.common.dagger.DaggerApplicationComponent;

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

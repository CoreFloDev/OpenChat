package io.coreflodev.httpcacheexample.chat.dagger;

import dagger.Component;
import io.coreflodev.httpcacheexample.chat.ChatActivity;
import io.coreflodev.httpcacheexample.common.dagger.ApplicationComponent;

@ChatScope
@Component(dependencies = ApplicationComponent.class, modules = ChatModule.class)
public interface ChatComponent {

    void inject(ChatActivity chatActivity);
}

package io.coreflodev.httpcacheexample.chat.dagger;

import dagger.Component;
import io.coreflodev.httpcacheexample.chat.ChatActivity;
import io.coreflodev.httpcacheexample.common.dagger.ApplicationModule;

@ChatScope
@Component(dependencies = ApplicationModule.class, modules = ChatModule.class)
public interface ChatComponent {

    void inject(ChatActivity chatActivity);
}

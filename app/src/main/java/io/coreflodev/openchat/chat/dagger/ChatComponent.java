package io.coreflodev.openchat.chat.dagger;

import dagger.Component;
import io.coreflodev.openchat.chat.ChatActivity;
import io.coreflodev.openchat.common.dagger.ApplicationComponent;

@ChatScope
@Component(dependencies = ApplicationComponent.class, modules = ChatModule.class)
public interface ChatComponent {

    void inject(ChatActivity chatActivity);
}

package io.coreflodev.httpcacheexample.common.dagger;

import dagger.Component;
import io.coreflodev.httpcacheexample.api.ChatService;

@ApplicationScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    ChatService chatService();
}

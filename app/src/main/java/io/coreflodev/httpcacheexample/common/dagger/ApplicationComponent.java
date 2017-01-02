package io.coreflodev.httpcacheexample.common.dagger;

import dagger.Component;
import io.coreflodev.httpcacheexample.chat.ChatMessage;

@ApplicationScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

}

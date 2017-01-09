package io.coreflodev.openchat.common.dagger;

import android.content.Context;

import dagger.Component;
import io.coreflodev.openchat.api.ChatService;

@ApplicationScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    ChatService chatService();

    Context context();
}

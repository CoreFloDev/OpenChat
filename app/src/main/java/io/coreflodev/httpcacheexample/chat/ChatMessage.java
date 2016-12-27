package io.coreflodev.httpcacheexample.chat;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.util.Date;

@AutoValue
public abstract class ChatMessage {

    public static ChatMessage create(String pseudo, String message, Date date) {
        return new AutoValue_ChatMessage(pseudo, message, date);
    }

    @NonNull
    public abstract String pseudo();

    @NonNull
    public abstract String message();

    @NonNull
    public abstract Date date();
}

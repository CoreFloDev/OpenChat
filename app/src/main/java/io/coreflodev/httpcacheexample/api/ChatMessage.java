package io.coreflodev.httpcacheexample.api;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@AutoValue
public abstract class ChatMessage {

    public static ChatMessage create(String pseudo, String message, Date date) {
        return new AutoValue_ChatMessage(pseudo, message, date);
    }

    public static TypeAdapter<ChatMessage> typeAdapter(Gson gson) {
        return new AutoValue_ChatMessage.GsonTypeAdapter(gson);
    }

    @NonNull
    @SerializedName("pseudo")
    public abstract String pseudo();

    @NonNull
    @SerializedName("message")
    public abstract String message();

    @NonNull
    @SerializedName("date")
    public abstract Date date();
}

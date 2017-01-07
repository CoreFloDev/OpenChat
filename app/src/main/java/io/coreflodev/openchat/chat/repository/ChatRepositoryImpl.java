package io.coreflodev.openchat.chat.repository;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.coreflodev.openchat.api.ChatMessage;
import io.coreflodev.openchat.common.network.AutoValueGsonTypeAdapterFactory;

public class ChatRepositoryImpl implements ChatRepository {


    private static final String KEY_CHAT_MESSAGES = "KEY_CHAT_MESSAGES";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public ChatRepositoryImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .registerTypeAdapterFactory(AutoValueGsonTypeAdapterFactory.create()).create();
    }

    @Override
    public void save(List<ChatMessage> messages) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CHAT_MESSAGES, gson.toJson(messages));
        editor.apply();
    }

    @Override
    public List<ChatMessage> read() {
        String json = sharedPreferences.getString(KEY_CHAT_MESSAGES, null);
        Type listType = new TypeToken<List<ChatMessage>>(){}.getType();
        return json == null ? null : gson.fromJson(json, listType);
    }
}

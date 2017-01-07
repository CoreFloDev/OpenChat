package io.coreflodev.openchat.chat.repository;

import java.util.List;

import io.coreflodev.openchat.api.ChatMessage;

public interface ChatRepository {

    void save(List<ChatMessage> messages);

    List<ChatMessage> read();
}

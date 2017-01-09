package io.coreflodev.openchat.chat.repository;

import java.util.List;

import io.coreflodev.openchat.api.ChatMessage;

public interface ChatRepository {

    List<ChatMessage> read();

    void save(List<ChatMessage> chatMessages);
}

package io.coreflodev.openchat.chat.repository;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import io.coreflodev.openchat.api.ChatMessage;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChatRepositoryImplTest {

    @Mock
    SharedPreferences sharedPreferencesMock;
    @Mock
    SharedPreferences.Editor editorMock;

    private ChatRepositoryImpl chatRepository;
    private static final String KEY_CHAT_MESSAGES = "KEY_CHAT_MESSAGES";

    @Before
    public void setup() {
        chatRepository = new ChatRepositoryImpl(sharedPreferencesMock);
        when(sharedPreferencesMock.edit()).thenReturn(editorMock);
    }

    @Test
    public void testSave() {
        List<ChatMessage> messageList = new ArrayList<>();
        messageList.add(ChatMessage.create("pseudo", "message", new GregorianCalendar(2017,1,2).getTime()));
        chatRepository.save(messageList);
        verify(editorMock).putString(KEY_CHAT_MESSAGES, "[{\"pseudo\":\"pseudo\",\"message\":\"message\",\"date\":\"2017-02-02T00:00:00.000Z\"}]");
        verify(editorMock).apply();
    }

    @Test
    public void testRead() {
        when(sharedPreferencesMock.getString(KEY_CHAT_MESSAGES, null)).thenReturn("[{\"pseudo\":\"pseudo\",\"message\":\"message\",\"date\":\"2017-02-02T00:00:00.000Z\"}]");
        List<ChatMessage> chatMessages = chatRepository.read();
        assertEquals(chatMessages.size(), 1);
        assertEquals(chatMessages.get(0).pseudo(), "pseudo");
        assertEquals(chatMessages.get(0).message(), "message");
        assertEquals(chatMessages.get(0).date().toString(), new GregorianCalendar(2017,1,2).getTime().toString());
    }
}

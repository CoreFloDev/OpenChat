package io.coreflodev.openchat.chat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.coreflodev.openchat.RxPluginTestRule;
import io.coreflodev.openchat.api.ChatMessage;
import io.coreflodev.openchat.api.ChatService;
import io.coreflodev.openchat.chat.repository.ChatRepository;
import io.reactivex.Observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// TODO test edge case
@RunWith(MockitoJUnitRunner.class)
public class ChatPresenterTest {

    @Rule
    public RxPluginTestRule rule = new RxPluginTestRule();

    @Mock
    ChatService chatServiceMock;
    @Mock
    ChatRepository chatRepositoryMock;
    @Mock
    ChatPresenter.View chatPresenterViewMock;
    @Mock
    ChatPresenter.View chatPresenterViewSecondMock;
    @Captor
    ArgumentCaptor<List<ChatMessage>> listChatMessageCaptor;
    @Captor
    ArgumentCaptor<ChatMessage> chatMessageCaptor;

    private ChatPresenter chatPresenter;

    private List<ChatMessage> testMessages;
    private String testMessageString;

    @Before
    public void setup() {
        testMessages = new ArrayList<>();
        testMessages.add(ChatMessage.create("test", "message", new Date()));
        testMessages.add(ChatMessage.create("test2", "message2", new Date()));
        testMessageString = "testMessageString";

        chatPresenter = new ChatPresenter(chatServiceMock, chatRepositoryMock);
    }

    @Test
    public void testAttachView() {
        when(chatServiceMock.getMessages()).thenReturn(Observable.just(testMessages));
        when(chatServiceMock.addMessage(chatMessageCaptor.capture())).thenReturn(Observable.just(testMessages.get(0)));
        when(chatPresenterViewMock.getNewMessage()).thenReturn(Observable.just(testMessageString));

        chatPresenter.attachView(chatPresenterViewMock);

        verify(chatPresenterViewMock).setListOfMessage(listChatMessageCaptor.capture());
        for (int i = 0; i < testMessages.size(); i++) {
            assertEquals(listChatMessageCaptor.getValue().get(i).toString(), testMessages.get(i).toString());
        }
        verify(chatRepositoryMock).save(listChatMessageCaptor.getValue());

        ChatMessage messageSend = chatMessageCaptor.getValue();
        assertEquals(messageSend.pseudo(), "test");
        assertEquals(messageSend.message(), testMessageString);
        assertNotNull(messageSend.date());

        chatPresenter.detachView();
        when(chatPresenterViewSecondMock.getNewMessage()).thenReturn(Observable.just(testMessageString));
        chatPresenter.attachView(chatPresenterViewSecondMock);

        verify(chatPresenterViewSecondMock).setListOfMessage(listChatMessageCaptor.capture());
        for (int i = 0; i < testMessages.size(); i++) {
            assertEquals(listChatMessageCaptor.getValue().get(i).toString(), testMessages.get(i).toString());
        }

        chatPresenter.destroy();
    }
}

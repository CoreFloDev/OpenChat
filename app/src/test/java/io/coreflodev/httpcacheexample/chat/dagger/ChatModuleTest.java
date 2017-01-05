package io.coreflodev.httpcacheexample.chat.dagger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.coreflodev.httpcacheexample.api.ChatService;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ChatModuleTest {

    @Mock
    ChatService chatServiceMock;

    private ChatModule chatModule;

    @Before
    public void setup() {
        chatModule = new ChatModule();
    }

    @Test
    public void testProvideChatPresenter() {
        assertNotNull(chatModule.providesChatPresenter(chatServiceMock));
    }
}

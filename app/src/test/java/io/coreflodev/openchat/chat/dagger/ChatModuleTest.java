package io.coreflodev.openchat.chat.dagger;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.coreflodev.openchat.api.ChatService;
import io.coreflodev.openchat.chat.repository.ChatRepository;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ChatModuleTest {

    @Mock
    ChatService chatServiceMock;
    @Mock
    ChatRepository chatRepositoryMock;
    @Mock
    SharedPreferences sharedPreferencesMock;

    private ChatModule chatModule;

    @Before
    public void setup() {
        chatModule = new ChatModule();
    }

    @Test
    public void testProvideChatPresenter() {
        assertNotNull(chatModule.providesChatPresenter(chatServiceMock, chatRepositoryMock));
    }

    @Test
    public void testProvideChatRepository() {
        assertNotNull(chatModule.providesChatRepository(sharedPreferencesMock));
    }
}

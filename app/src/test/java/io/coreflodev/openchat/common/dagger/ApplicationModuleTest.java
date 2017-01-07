package io.coreflodev.openchat.common.dagger;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.coreflodev.openchat.api.ChatService;
import io.coreflodev.openchat.common.network.NetworkService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationModuleTest {

    @Mock
    NetworkService networkServiceMock;
    @Mock
    ChatService chatServiceMock;
    @Mock
    Context contextMock;

    private ApplicationModule applicationModule;

    @Before
    public void setup() {
        applicationModule = new ApplicationModule(contextMock);
    }

    @Test
    public void testProvideNetworkService() {
        assertNotNull(applicationModule.provideNetworkService());
    }

    @Test
    public void testProvideChatServices() {
        when(networkServiceMock.createService(ChatService.class)).thenReturn(chatServiceMock);
        assertEquals(chatServiceMock, applicationModule.provideChatService(networkServiceMock));
    }
}

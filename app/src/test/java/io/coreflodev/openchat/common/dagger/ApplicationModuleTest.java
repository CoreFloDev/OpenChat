package io.coreflodev.openchat.common.dagger;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.coreflodev.openchat.api.ChatService;
import io.coreflodev.openchat.common.network.NetworkService;
import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationModuleTest {

    @Mock
    Context contextMock;
    @Mock
    NetworkService networkServiceMock;
    @Mock
    ChatService chatServiceMock;

    private ApplicationModule applicationModule;

    @Before
    public void setup() {
        applicationModule = new ApplicationModule(contextMock);
    }

    @Test
    public void testProvideNetworkService() {
        assertNotNull(applicationModule.provideNetworkService(new OkHttpClient(), GsonConverterFactory.create()));
    }

    @Test
    public void testProvideChatServices() {
        when(networkServiceMock.createService(ChatService.class)).thenReturn(chatServiceMock);
        assertEquals(chatServiceMock, applicationModule.provideChatService(networkServiceMock));
    }

    @Test
    public void testProvideOkHttpClient() {
        assertNotNull(applicationModule.provideOkHttpClient());
    }

    @Test
    public void testProvideGsonConverterFactory() {
        assertNotNull(applicationModule.provideGsonConverterFactory());
    }
}

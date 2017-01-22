package io.coreflodev.openchat.common.network;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertNotNull;

public class NetworkServiceTest {

    private NetworkService networkService;

    @Before
    public void setup() {
        networkService = new NetworkService(new OkHttpClient(), GsonConverterFactory.create());
    }

    @Test
    public void testCreateService() {
        assertNotNull(networkService.createService(TestInterface.class));
    }

    public interface TestInterface {

    }
}

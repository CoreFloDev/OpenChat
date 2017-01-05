package io.coreflodev.httpcacheexample.common.network;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class NetworkServiceTest {

    private NetworkService networkService;

    @Before
    public void setup() {
        networkService = new NetworkService();
    }

    @Test
    public void testCreateService() {
        assertNotNull(networkService.createService(TestInterface.class));
    }

    public interface TestInterface {

    }
}

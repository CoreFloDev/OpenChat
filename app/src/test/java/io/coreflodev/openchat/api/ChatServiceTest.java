package io.coreflodev.openchat.api;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChatServiceTest {

    @ClassRule
    public static ApiTestRule apiTestRule = new ApiTestRule();

    private ChatService chatService;

    @Before
    public void setup() {
        chatService = apiTestRule.create(ChatService.class);
    }

    @Test
    public void testGetMessages() {
        apiTestRule.enqueue("[" +
                "  {" +
                "    \"pseudo\": \"test\"," +
                "    \"message\": \"message from server\"," +
                "    \"date\": \"2017-01-06T14:57:33.070Z\"" +
                "  }" +
                "]");
        List<ChatMessage> chatMessages = chatService.getMessages().blockingFirst();
        assertEquals(chatMessages.size(), 1);
        assertEquals(chatMessages.get(0).pseudo(), "test");
        assertEquals(chatMessages.get(0).message(), "message from server");
        GregorianCalendar expectedDate = new GregorianCalendar(2017, 0, 6, 14, 57, 33);
        assertEquals(chatMessages.get(0).date().toString(), expectedDate.getTime().toString());
    }

    @Test
    public void testAddMessage() {
        apiTestRule.enqueue("{" +
                "    \"pseudo\": \"test\"," +
                "    \"message\": \"message from server\"," +
                "    \"date\": \"2017-01-06T14:57:33.070Z\"" +
                "}");
        GregorianCalendar expectedDate = new GregorianCalendar(2017, 0, 6, 14, 57, 33);
        ChatMessage chatMessage = ChatMessage.create("test", "message from server", expectedDate.getTime());

        ChatMessage chatMessageTest = chatService.addMessage(chatMessage).blockingFirst();
        assertEquals(chatMessageTest.toString(), chatMessage.toString());
    }
}

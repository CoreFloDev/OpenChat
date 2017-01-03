package io.coreflodev.httpcacheexample.api;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ChatService {

    @POST("/message")
    Observable<ChatMessage> addMessage(@Body ChatMessage chatMessage);

    @GET("/messages")
    Observable<List<ChatMessage>> getMessages();
}

package io.coreflodev.httpcacheexample.api;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ChatService {

    @GET("/messages")
    Observable<List<ChatMessage>> getMessages();
}

package io.coreflodev.httpcacheexample.chat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.coreflodev.httpcacheexample.common.mvp.Presenter;
import io.coreflodev.httpcacheexample.common.mvp.PresenterView;

public class ChatPresenter extends Presenter<ChatPresenter.View> {

    private List<ChatMessage> messages;

    public ChatPresenter() {
        messages = new ArrayList<>();
        messages.add(ChatMessage.create("test", "My first message", new GregorianCalendar(2016,12,2).getGregorianChange()));
        messages.add(ChatMessage.create("test2", "My second message", new GregorianCalendar(2016,12,2).getGregorianChange()));
        messages.add(ChatMessage.create("test", "My third message", new GregorianCalendar(2016,12,2).getGregorianChange()));
        messages.add(ChatMessage.create("test4", "My last message", new GregorianCalendar(2016,12,2).getGregorianChange()));

    }

    @Override
    public void attachView(View view) {
        super.attachView(view);
        view.setInitialListOfMessage(messages);
    }

    @Override
    public void destroy() {

    }

    public interface View extends PresenterView {

        void setInitialListOfMessage(List<ChatMessage> messages);

        void addNewMessage(ChatMessage chatMessage);
    }
}

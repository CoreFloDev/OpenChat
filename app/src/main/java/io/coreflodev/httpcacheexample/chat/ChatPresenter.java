package io.coreflodev.httpcacheexample.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.coreflodev.httpcacheexample.api.ChatMessage;
import io.coreflodev.httpcacheexample.api.ChatService;
import io.coreflodev.httpcacheexample.common.mvp.Presenter;
import io.coreflodev.httpcacheexample.common.mvp.PresenterView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChatPresenter extends Presenter<ChatPresenter.View> {

    private List<ChatMessage> messages;

    private Disposable newMessages;
    private Disposable oldMessages;

    private ChatService chatService;

    public ChatPresenter(ChatService chatService) {
        this.chatService = chatService;
        messages = new ArrayList<>();
        messages.add(ChatMessage.create("test", "My first message", new GregorianCalendar(2016, 12, 2).getGregorianChange()));
        messages.add(ChatMessage.create("test2", "My second message", new GregorianCalendar(2016, 12, 2).getGregorianChange()));
        messages.add(ChatMessage.create("test", "My third message", new GregorianCalendar(2016, 12, 2).getGregorianChange()));
        messages.add(ChatMessage.create("test4", "My last message", new GregorianCalendar(2016, 12, 2).getGregorianChange()));
    }

    @Override
    public void attachView(View view) {
        super.attachView(view);

        oldMessages = chatService.getMessages()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(messages -> {
                    this.messages = messages;
                    if (isViewAttached()) {
                        view.setInitialListOfMessage(new ArrayList<>(messages));
                    }
                });

        newMessages = view.getNewMessage()
                .flatMap(message -> chatService.addMessage(ChatMessage.create("test", message, new Date()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()))
                .subscribe(newMessage -> {
                    messages.add(newMessage);
                    if (isViewAttached()) {
                        view.addNewMessage(newMessage);
                    }
                });
    }

    @Override
    public void destroy() {
        if (newMessages != null && !newMessages.isDisposed()) {
            newMessages.dispose();
        }
        if (oldMessages != null && !oldMessages.isDisposed()) {
            oldMessages.dispose();
        }
    }

    public interface View extends PresenterView {

        void setInitialListOfMessage(List<ChatMessage> messages);

        Observable<String> getNewMessage();

        void addNewMessage(ChatMessage chatMessage);
    }
}

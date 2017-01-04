package io.coreflodev.httpcacheexample.chat;

import java.util.ArrayList;
import java.util.Date;
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

    private Disposable newMessagesDisposable;
    private Disposable oldMessagesDisposable;

    private ChatService chatService;

    public ChatPresenter(ChatService chatService) {
        this.chatService = chatService;
        this.messages = new ArrayList<>();
    }

    @Override
    public void attachView(View view) {
        super.attachView(view);

        if (messages.size() == 0) {
            oldMessagesDisposable = chatService.getMessages()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(messages -> {
                        this.messages.addAll(messages);
                        if (isViewAttached()) {
                            view.setInitialListOfMessage(new ArrayList<>(messages));
                        }
                    });
        }

        if (newMessagesDisposable == null || newMessagesDisposable.isDisposed()) {
            newMessagesDisposable = view.getNewMessage()
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
    }

    @Override
    public void destroy() {
        if (newMessagesDisposable != null && !newMessagesDisposable.isDisposed()) {
            newMessagesDisposable.dispose();
        }
        if (oldMessagesDisposable != null && !oldMessagesDisposable.isDisposed()) {
            oldMessagesDisposable.dispose();
        }
    }

    public interface View extends PresenterView {

        void setInitialListOfMessage(List<ChatMessage> messages);

        Observable<String> getNewMessage();

        void addNewMessage(ChatMessage chatMessage);
    }
}

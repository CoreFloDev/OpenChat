package io.coreflodev.openchat.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.coreflodev.openchat.api.ChatMessage;
import io.coreflodev.openchat.api.ChatService;
import io.coreflodev.openchat.common.mvp.Presenter;
import io.coreflodev.openchat.common.mvp.PresenterView;
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
                    .repeatWhen(o -> o.concatMap(v -> Observable.interval(3, TimeUnit.SECONDS)))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(messages -> {
                        if (this.messages.size() != messages.size()) {
                            this.messages.clear();
                            this.messages.addAll(messages);
                            if (isViewAttached()) {
                                getView().setListOfMessage(new ArrayList<>(messages));
                            }
                        }
                    });
        } else {
            getView().setListOfMessage(new ArrayList<>(messages));
        }

        if (newMessagesDisposable == null || newMessagesDisposable.isDisposed()) {
            newMessagesDisposable = getView().getNewMessage()
                    .flatMap(message -> chatService.addMessage(ChatMessage.create("test", message, new Date()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()))
                    .subscribe();
        }
    }

    @Override
    public void detachView() {
        if (newMessagesDisposable != null && !newMessagesDisposable.isDisposed()) {
            newMessagesDisposable.dispose();
        }
        super.detachView();
    }

    @Override
    public void destroy() {
        if (oldMessagesDisposable != null && !oldMessagesDisposable.isDisposed()) {
            oldMessagesDisposable.dispose();
        }
    }

    public interface View extends PresenterView {

        void setListOfMessage(List<ChatMessage> messages);

        Observable<String> getNewMessage();
    }
}

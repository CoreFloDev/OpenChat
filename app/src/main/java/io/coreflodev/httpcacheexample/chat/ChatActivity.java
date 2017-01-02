package io.coreflodev.httpcacheexample.chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import butterknife.BindView;
import io.coreflodev.httpcacheexample.R;
import io.coreflodev.httpcacheexample.common.mvp.MVPBaseActivity;
import io.reactivex.Observable;
import io.reactivex.android.MainThreadDisposable;

public class ChatActivity extends MVPBaseActivity<ChatPresenter.View> implements ChatPresenter.View {

    @BindView(R.id.rv_chat_message_list)
    RecyclerView rvMessageList;

    @BindView(R.id.et_chat_new_message_content)
    EditText etMessageContent;
    @BindView(R.id.ib_chat_new_message_send)
    ImageButton ivSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMessageList.setLayoutManager(linearLayoutManager);
        ChatAdapter chatAdapter = new ChatAdapter();
        rvMessageList.setAdapter(chatAdapter);

        chatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                linearLayoutManager.scrollToPosition(positionStart);
            }
        });

        ChatPresenter chatPresenter = new ChatPresenter();

        addPresenter(chatPresenter, this);
    }

    @Override
    public void setInitialListOfMessage(List<ChatMessage> messages) {
        if (rvMessageList.getAdapter() != null) {
            ((ChatAdapter) rvMessageList.getAdapter()).addMessages(messages);
        }
    }

    @Override
    public Observable<String> getNewMessage() {
        return Observable.create(emitter -> {
            MainThreadDisposable.verifyMainThread();

            ivSend.setOnClickListener(view -> {
                if (!etMessageContent.getText().toString().isEmpty()) {
                    emitter.onNext(etMessageContent.getText().toString());
                    etMessageContent.setText("");
                }
            });

            emitter.setDisposable(new MainThreadDisposable() {
                @Override
                protected void onDispose() {
                    ivSend.setOnClickListener(null);
                }
            });
        });
    }

    @Override
    public void addNewMessage(ChatMessage chatMessage) {
        if (rvMessageList.getAdapter() != null) {
            ChatAdapter chatAdapter = (ChatAdapter) rvMessageList.getAdapter();
            chatAdapter.addMessage(chatMessage);
        }
    }
}

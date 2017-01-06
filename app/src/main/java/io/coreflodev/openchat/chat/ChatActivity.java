package io.coreflodev.openchat.chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.coreflodev.openchat.ChatApplication;
import io.coreflodev.openchat.R;
import io.coreflodev.openchat.api.ChatMessage;
import io.coreflodev.openchat.common.mvp.MVPBaseActivity;
import io.reactivex.Observable;
import io.reactivex.android.MainThreadDisposable;

public class ChatActivity extends MVPBaseActivity<ChatPresenter.View> implements ChatPresenter.View {

    @BindView(R.id.rv_chat_message_list)
    RecyclerView rvMessageList;

    @BindView(R.id.et_chat_new_message_content)
    EditText etMessageContent;
    @BindView(R.id.ib_chat_new_message_send)
    ImageView ivSend;

    @Inject
    ChatPresenter chatPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ((ChatApplication) getApplication()).getChatComponent().inject(this);

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
        addPresenter(chatPresenter, this);
    }

    @Override
    public void setListOfMessage(List<ChatMessage> messages) {
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
}

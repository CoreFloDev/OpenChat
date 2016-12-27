package io.coreflodev.httpcacheexample.chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.coreflodev.httpcacheexample.R;
import io.coreflodev.httpcacheexample.common.mvp.MVPBaseActivity;

public class ChatActivity extends MVPBaseActivity<ChatPresenter.View> implements ChatPresenter.View {

    @BindView(R.id.rv_chat_message_list)
    RecyclerView rvMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        rvMessageList.setLayoutManager(new LinearLayoutManager(this));

        ChatPresenter chatPresenter = new ChatPresenter();

        addPresenter(chatPresenter, this);
    }

    @Override
    public void setInitialListOfMessage(List<ChatMessage> messages) {
        rvMessageList.setAdapter(new ChatAdapter(messages));
    }

    @Override
    public void addNewMessage(ChatMessage chatMessage) {

    }
}

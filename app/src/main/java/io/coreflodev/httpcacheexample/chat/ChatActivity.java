package io.coreflodev.httpcacheexample.chat;

import android.os.Bundle;

import io.coreflodev.httpcacheexample.R;
import io.coreflodev.httpcacheexample.common.mvp.MVPBaseActivity;

public class ChatActivity extends MVPBaseActivity<ChatPresenter.View> implements ChatPresenter.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addPresenter(new ChatPresenter(), this);
    }
}

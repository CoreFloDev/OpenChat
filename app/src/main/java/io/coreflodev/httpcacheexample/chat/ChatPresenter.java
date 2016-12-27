package io.coreflodev.httpcacheexample.chat;

import io.coreflodev.httpcacheexample.common.mvp.Presenter;
import io.coreflodev.httpcacheexample.common.mvp.PresenterView;

public class ChatPresenter extends Presenter<ChatPresenter.View> {

    @Override
    public void attachView(View view) {
        super.attachView(view);

    }

    @Override
    public void destroy() {

    }

    public interface View extends PresenterView {

    }
}

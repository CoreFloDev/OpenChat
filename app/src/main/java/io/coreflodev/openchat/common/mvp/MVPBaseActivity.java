package io.coreflodev.openchat.common.mvp;

import io.coreflodev.openchat.common.BaseActivity;

public class MVPBaseActivity<V extends PresenterView> extends BaseActivity {

    private Presenter<V> presenter;

    protected void addPresenter(Presenter<V> presenter, V presenterView) {
        this.presenter = presenter;
        presenter.attachView(presenterView);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        if (isFinishing()) {
            presenter.destroy();
        }
        super.onDestroy();
    }
}

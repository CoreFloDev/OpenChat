package io.coreflodev.httpcacheexample.common.mvp;

import android.support.v7.app.AppCompatActivity;

public class MVPBaseActivity<V extends PresenterView> extends AppCompatActivity {

    private Presenter<V> presenter;

    protected void addPresenter(Presenter<V> presenter, V presenterView) {
        this.presenter = presenter;
        presenter.attachView(presenterView);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        if(isFinishing()) {
            presenter.destroy();
        }
        super.onDestroy();
    }
}

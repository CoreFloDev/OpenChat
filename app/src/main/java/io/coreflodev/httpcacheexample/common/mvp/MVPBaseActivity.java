package io.coreflodev.httpcacheexample.common.mvp;

import android.support.v7.app.AppCompatActivity;

public class MVPBaseActivity<V extends PresenterView> extends AppCompatActivity {

    private Presenter<V> presenter;
    private V presenterView;

    protected void addPresenter(Presenter<V> presenter, V presenterView) {
        this.presenter = presenter;
        this.presenterView = presenterView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(presenterView);
    }

    @Override
    protected void onStop() {
        presenter.detachView();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if(isFinishing()) {
            presenter.destroy();
        }
        super.onDestroy();
    }
}

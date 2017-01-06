package io.coreflodev.openchat.common.mvp;

public abstract class Presenter<T extends PresenterView> {

    private T view;

    public void attachView(T view) {
        this.view = view;
    }

    public boolean isViewAttached() {
        return view != null;
    }

    public T getView() {
        return view;
    }

    public void detachView() {
        view = null;
    }

    public abstract void destroy();
}

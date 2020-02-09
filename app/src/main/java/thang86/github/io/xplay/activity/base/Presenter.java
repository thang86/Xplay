package thang86.github.io.xplay.activity.base;

/**
 * Created by TranThang on 2/8/2020.
 */

public class Presenter<T extends BaseView> implements BasePresenter<T> {
    private T mView;

    @Override
    public void subscribe(T view) {
        this.mView = view;


    }

    @Override
    public void unsubscribe() {
        this.mView = null;
    }

    public T getView() {
        return this.mView;
    }

    public boolean isViewAttached() {
        return this.mView != null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new ViewNotAttachedException();
    }
}

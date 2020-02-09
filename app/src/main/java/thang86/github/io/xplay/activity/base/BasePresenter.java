package thang86.github.io.xplay.activity.base;

/**
 * Created by TranThang on 2/8/2020.
 */

public interface BasePresenter<V extends BaseView> {

    void subscribe(V view);

    void unsubscribe();
}

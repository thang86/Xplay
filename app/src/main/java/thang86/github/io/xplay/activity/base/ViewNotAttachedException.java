package thang86.github.io.xplay.activity.base;

/**
 * Created by TranThang on 2/8/2020.
 */

public class ViewNotAttachedException extends RuntimeException {
    ViewNotAttachedException() {
        super("Please call subscribe() before proceeding!");
    }
}

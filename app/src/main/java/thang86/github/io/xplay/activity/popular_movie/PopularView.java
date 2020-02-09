package thang86.github.io.xplay.activity.popular_movie;

import java.util.List;

import thang86.github.io.xplay.activity.base.BaseView;
import thang86.github.io.xplay.model.MoviePopular;

/**
 * Created by TranThang on 2/8/2020.
 */

public interface PopularView extends BaseView {
    void showProgress(boolean isShow);

    void showMovies(List<MoviePopular> movies);

    void showError();

}

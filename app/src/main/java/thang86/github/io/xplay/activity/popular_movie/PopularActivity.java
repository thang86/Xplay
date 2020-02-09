package thang86.github.io.xplay.activity.popular_movie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import thang86.github.io.xplay.R;
import thang86.github.io.xplay.model.MoviePopular;
import thang86.github.io.xplay.network.utils.ApiUtils;
import thang86.github.io.xplay.utils.ResponseCache;

/**
 * Created by TranThang on 2/8/2020.
 */

public class PopularActivity extends AppCompatActivity implements PopularView {

    private static final String TAG = "MoviesPopular";
    private PopularPresenter mPresenter;
    private List<MoviePopular> mMoviePopulars = new ArrayList<>();
    private ResponseCache mResponseCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        try {
            mResponseCache = new ResponseCache(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPresenter = new PopularPresenter(mResponseCache, ApiUtils.getMoviePopularRespone());
        mPresenter.subscribe(this);

        showProgress(true);
    }

    @Override
    public void showProgress(boolean isShow) {
        mPresenter.loadPopularMovie(true);
    }

    @Override
    public void showMovies(List<MoviePopular> movies) {
        mMoviePopulars.clear();
        mMoviePopulars.addAll(movies);
        int n = movies.size();
        System.out.println(n);
        Log.d(TAG, "showMovies: "+ mMoviePopulars.size());
    }

    @Override
    public void showError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}

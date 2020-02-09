package thang86.github.io.xplay.activity.popular_movie;

import android.util.Log;

import java.util.List;


import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import thang86.github.io.xplay.BuildConfig;
import thang86.github.io.xplay.activity.base.Presenter;
import thang86.github.io.xplay.model.MoviePopular;
import thang86.github.io.xplay.model.Response;
import thang86.github.io.xplay.network.MovieApiService;
import thang86.github.io.xplay.utils.ResponseCache;


/**
 * Created by TranThang on 2/8/2020.
 */

public class PopularPresenter extends Presenter<PopularView> {
    private final CompositeSubscription mCompositeSubscription;
    private ResponseCache mResponseCache;
    private MovieApiService mMovieApiService;

    public PopularPresenter(ResponseCache responseCache, MovieApiService movieApiService) {
        mCompositeSubscription = new CompositeSubscription();
        this.mResponseCache = responseCache;
        this.mMovieApiService = movieApiService;
    }

    @Override
    public void subscribe(PopularView view) {
        super.subscribe(view);
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        mCompositeSubscription.unsubscribe();
    }

    public void loadPopularMovie(boolean isCache) {
        Subscriber<List<MoviePopular>> subscriber = new Subscriber<List<MoviePopular>>() {
            @Override
            public void onCompleted() {
                getView().showProgress(false);
            }

            @Override
            public void onError(Throwable e) {
                getView().showProgress(false);
                getView().showError();
            }

            @Override
            public void onNext(List<MoviePopular> moviePopulars) {
                int n = moviePopulars.size();
                Log.d("BNHAGA", "onNext: ----------"+n);
                getView().showMovies(moviePopulars);
            }
        };
        mCompositeSubscription.add((isCache ? Observable.concat(getCacheObservable(), getPopularMovieObservable()) : getPopularMovieObservable())
                .filter(list -> list != null && list.size() > 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));

    }

    private Observable<List<MoviePopular>> getPopularMovieObservable() {
        return Observable.defer(new Func0<Observable<List<MoviePopular>>>() {
            @Override
            public Observable<List<MoviePopular>> call() {
                return mMovieApiService.getNowPlayingMovies(BuildConfig.API_KEY)
                        .flatMap(response -> Observable.just(response.getResults()))
                        .doOnNext(list -> mResponseCache.insert("movies", list));
            }
        });
    }

    private Observable<List<MoviePopular>> getCacheObservable() {
        return Observable.defer(() -> {
            List<MoviePopular> list = mResponseCache.get("movies");
            return Observable.just(list);
        });
    }


}

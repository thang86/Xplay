package thang86.github.io.xplay.activity.popular_movie;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import thang86.github.io.xplay.R;
import thang86.github.io.xplay.activity.popular_movie.adapter.PopularAdapter;
import thang86.github.io.xplay.model.MoviePopular;
import thang86.github.io.xplay.network.utils.ApiUtils;
import thang86.github.io.xplay.utils.ResponseCache;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularMovieFragment extends Fragment implements PopularView, SwipeRefreshLayout.OnRefreshListener, PopularAdapter.OnMovieClickListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    private PopularPresenter presenter;
    private PopularAdapter adapter;
    private List<MoviePopular> mMoviePopulars = new ArrayList<>();
    private ResponseCache responseCache;

    public PopularMovieFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_movie, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        try {
            responseCache = new ResponseCache(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        presenter = new PopularPresenter(responseCache, ApiUtils.getMoviePopularRespone());
        presenter.subscribe(this);
        adapter = new PopularAdapter(mMoviePopulars);
        adapter.setOnMovieClickListener(this);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);

        showProgress(true);
        return view;
    }

    @Override
    public void showProgress(boolean isShow) {
        if (isShow) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                    presenter.loadPopularMovie(true);
                }
            });
        } else
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMovies(List<MoviePopular> movies) {
        mMoviePopulars.clear();
        mMoviePopulars.addAll(movies);
        Log.d("BNHAGA", "showMovies: ------" + mMoviePopulars.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void onRefresh() {
        presenter.loadPopularMovie(false);
    }

    @Override
    public void onMovieClicked(MoviePopular movie, View view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsubscribe();
    }
}

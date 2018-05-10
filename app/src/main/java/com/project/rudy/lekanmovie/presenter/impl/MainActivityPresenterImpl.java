package com.project.rudy.lekanmovie.presenter.impl;

import android.util.Log;

import com.project.rudy.lekanmovie.model.MovieData;
import com.project.rudy.lekanmovie.presenter.IMainActivityPresenter;
import com.project.rudy.lekanmovie.server.MovieLoader;
import com.project.rudy.lekanmovie.view.activity.MainActivity;

import java.util.List;

import rx.Subscriber;

/**
 * Created by maoyan on 2018/5/7.
 */

public class MainActivityPresenterImpl implements IMainActivityPresenter{
    private MainActivity activity;

    public MainActivityPresenterImpl(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void loadMovieData() {
        MovieLoader.getMovieInfo("北京")
                .subscribe(new Subscriber<List<MovieData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<MovieData> movieData) {
//                        mDiscreteScrollView.setAdapter(new MovieListAdapter(movieData.get(MOVIE_RELEASE).getData()));
                        activity.setFragmentScrollViewAdapter(movieData);
                        Log.i("zhangle15", "onNext: ");

                    }
                });
    }
}

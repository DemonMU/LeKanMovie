package com.project.rudy.lekanmovie.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.rudy.lekanmovie.R;
import com.project.rudy.lekanmovie.model.Comment;
import com.project.rudy.lekanmovie.model.Movie;
import com.project.rudy.lekanmovie.model.MovieData;
import com.project.rudy.lekanmovie.model.MovieDataVO;
import com.project.rudy.lekanmovie.model.RequestResult;
import com.project.rudy.lekanmovie.server.APIConfigs;
import com.project.rudy.lekanmovie.server.RetrofitServiceManager;
import com.project.rudy.lekanmovie.server.api.MovieAPI;
import com.project.rudy.lekanmovie.view.adapter.MainMovieCommentListAdapter;
import com.project.rudy.lekanmovie.view.adapter.MovieListAdapter;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.internal.schedulers.NewThreadScheduler;
import rx.schedulers.Schedulers;

/**
 * Created by maoyan on 2018/5/2.
 */

public class MainMovieCommentFragment extends Fragment {

    @BindView(R.id.main_movie_rc)
    DiscreteScrollView mDiscreteScrollView;

    private MovieListAdapter movieListAdapter;
    private List<Movie> mMovieList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_main_movie, null);
       // ButterKnife.bind(this, mView);

        Subscription subscription = RetrofitServiceManager.getInstance().createService(MovieAPI.class).getMovieInfo(APIConfigs.APPKEY_JUHE, "北京")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RequestResult<MovieDataVO>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RequestResult<MovieDataVO> movieDataVORequestResult) {
                        Log.i("zhangle", "onNext: " + movieDataVORequestResult.getResult().getData().get(0).getData().get(0).getStar().getData().getLabel1().getName());
                        mMovieList = movieDataVORequestResult.getResult().getData().get(0).getData();
                        movieListAdapter = new MovieListAdapter(mMovieList);
                        mDiscreteScrollView.setAdapter(movieListAdapter);
                    }
                });

        return mView;
    }
}

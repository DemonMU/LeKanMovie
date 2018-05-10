package com.project.rudy.lekanmovie.view.activity;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.project.rudy.lekanmovie.R;
import com.project.rudy.lekanmovie.model.Movie;
import com.project.rudy.lekanmovie.model.MovieData;
import com.project.rudy.lekanmovie.model.User;
import com.project.rudy.lekanmovie.presenter.impl.MainActivityPresenterImpl;
import com.project.rudy.lekanmovie.view.adapter.MainMovieCommentListAdapter;
import com.project.rudy.lekanmovie.view.fragment.MainMovieCommentFragment;
import com.project.rudy.lekanmovie.model.Comment;
import com.project.rudy.lekanmovie.view.fragment.MovieListFragment;
import com.project.rudy.lekanmovie.view.iview.IMainActivity;
import com.project.rudy.lekanmovie.view.widget.UnScrollableViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.project.rudy.lekanmovie.view.fragment.MovieListFragment.MOVIE_RELEASE;
import static com.project.rudy.lekanmovie.view.fragment.MovieListFragment.MOVIE_WILL_RELEASE;


/**
 * Created by maoyan on 2018/5/2.
 */

public class MainActivity extends BaseActivity implements IMainActivity {

   /* @BindView(R.id.view_pager)
    UnScrollableViewPager mViewPager;*/
    /*@BindView(R.id.radio_group)
    RadioGroup mRadioGroup;*/
   /* @BindView(R.id.now_radio)
    RadioButton mNowBtn;
    @BindView(R.id.coming_radio)
    RadioButton mComingBtn;*/

    private FragmentManager mFragmentManager;
    private MovieListFragment mNowMovieListFragment;
    private MovieListFragment mComingMovieListFragment;
    private List<MovieListFragment> mMovieListFragment;
    private MainActivityPresenterImpl mMainActivityPresenter;


    public MainActivityPresenterImpl getMainActivityPresenter() {
        if (mMainActivityPresenter == null) {
            mMainActivityPresenter = new MainActivityPresenterImpl(this);
        }

        return mMainActivityPresenter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);

     /*   mMovieListFragment = new ArrayList<>();

        mComingMovieListFragment = MovieListFragment.newInstance(1);
        mMovieListFragment.add(mNowMovieListFragment);
        mMovieListFragment.add(mComingMovieListFragment);*/

        mNowMovieListFragment = new MovieListFragment();
        mFragmentManager = getFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.activity_empty_content, mNowMovieListFragment).commit();
/*
        mNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("zhangle15", "onClick: ");
            }
        });
        mComingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager.beginTransaction().replace(R.id.activity_empty_content, mComingMovieListFragment).commit();
            }
        });*/

    }


    @Override
    public void setFragmentScrollViewAdapter(List<MovieData> movieData) {
//        Log.i("zhangle15", "setFragmentScrollViewAdapter: "+(int)((MovieListFragment) mFragmentManager.findFragmentById(0)).getArguments().get("id"));
        /*if ((int)((MovieListFragment) mFragmentManager.findFragmentById(0)).getArguments().get("id") == 0){
            mNowMovieListFragment.setScrollViewAdapter(movieData.get(MOVIE_RELEASE).getData());

        }
        if ((int )((MovieListFragment) mFragmentManager.findFragmentById(1)).getArguments().get("id") == 1){
            mComingMovieListFragment.setScrollViewAdapter(movieData.get(MOVIE_WILL_RELEASE).getData());
        }*/
        mNowMovieListFragment.setScrollViewAdapter(movieData.get(MOVIE_RELEASE).getData());

    }

}

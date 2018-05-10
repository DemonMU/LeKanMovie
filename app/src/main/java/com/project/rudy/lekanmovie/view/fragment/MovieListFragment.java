package com.project.rudy.lekanmovie.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.rudy.lekanmovie.R;
import com.project.rudy.lekanmovie.model.Movie;
import com.project.rudy.lekanmovie.model.MovieData;
import com.project.rudy.lekanmovie.server.MovieLoader;
import com.project.rudy.lekanmovie.view.activity.MainActivity;
import com.project.rudy.lekanmovie.view.adapter.MovieListAdapter;
import com.project.rudy.lekanmovie.view.widget.BlurTransformation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by maoyan on 2018/5/6.
 */

public class MovieListFragment extends Fragment implements DiscreteScrollView.ScrollStateChangeListener<MovieListAdapter.MovieVH> {

    public static int MOVIE_RELEASE = 0;
    public static int MOVIE_WILL_RELEASE = 1;
    @BindView(R.id.bg_img1)
    ImageView mBgImg1;
    @BindView(R.id.bg_img2)
    ImageView mBgImg2;
    @BindView(R.id.main_movie_rc)
    DiscreteScrollView mDiscreteScrollView;

    private MovieListAdapter movieListAdapter;
    private List<Movie> mMovieList;
    private MainActivity mActivity;
    private BlurTransformation mBlurTransformation;
    private float mPrePosition;
    private boolean isIntentTriggered; //是否滑动制动
    private int mMaxIndex;
    private int mPrePos;

    public static MovieListFragment newInstance(int id) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_main_movie, null);
        ButterKnife.bind(this, fragmentView);
        mBlurTransformation = new BlurTransformation(mActivity, 10);
        mDiscreteScrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());
        mDiscreteScrollView.addScrollStateChangeListener(this);
        loadData();

        return fragmentView;

    }

    //异步加载数据，加载成功后绑定监听器
    private void loadData() {
        mActivity.getMainActivityPresenter().loadMovieData();
    }

    //加载完数据后会走到这个方法
    public void setScrollViewAdapter(List<Movie> movies) {
        mMovieList = movies;
        mDiscreteScrollView.setAdapter(new MovieListAdapter(movies));
        displayBgImage(0, mBgImg1);
    }

    private void displayBgImage(int index, ImageView imageView) {
        if (mActivity != null) {
            Glide.with(mActivity)
                    .load(mMovieList.get(index).getPoster())
                    .transform(mBlurTransformation)
                    .crossFade()
                    .into(imageView);
        }

    }

    @Override
    public void onScrollStart(@NonNull MovieListAdapter.MovieVH currentItemHolder, int adapterPosition) {
        Log.i("zhangle15", "onScrollStart: " + adapterPosition);

    }

    @Override
    public void onScrollEnd(@NonNull MovieListAdapter.MovieVH currentItemHolder, int adapterPosition) {
        Log.i("zhangle15", "onScrollEnd: " + adapterPosition);
        mMaxIndex = adapterPosition > mMaxIndex ? adapterPosition : mMaxIndex;
        currentItemHolder.mNameText.setAlpha(1f);
        if (currentItemHolder.mInfoLayout.getVisibility() == View.VISIBLE) {
            currentItemHolder.mInfoLayout.setAlpha(1f);
        }
        int size = mMovieList.size();
        if (mMovieList != null && size > 0) {
            MovieListAdapter.MovieVH vh;

            if (adapterPosition == 0 && size > 1) {
                vh = (MovieListAdapter.MovieVH) mDiscreteScrollView.getViewHolder(adapterPosition + 1);
                if (vh != null) {
                    vh.mNameText.setAlpha(0);

                    if (mPrePos != adapterPosition) {
                        vh.mOpenInfoImg.setVisibility(View.VISIBLE);
                        vh.mInfoLayout.setVisibility(View.INVISIBLE);
                    }
                }
            } else if (adapterPosition == size - 1 && size > 1) {
                vh = (MovieListAdapter.MovieVH) mDiscreteScrollView.getViewHolder(adapterPosition - 1);
                if (vh != null) {
                    vh.mNameText.setAlpha(0);

                    if (mPrePos != adapterPosition) {
                        vh.mOpenInfoImg.setVisibility(View.VISIBLE);
                        vh.mInfoLayout.setVisibility(View.INVISIBLE);
                    }
                }
            } else if (adapterPosition > 0 && adapterPosition + 1 < size - 1) {
                vh = (MovieListAdapter.MovieVH) mDiscreteScrollView.getViewHolder(adapterPosition + 1);
                if (vh != null) {
                    vh.mNameText.setAlpha(0);

                    if (mPrePos != adapterPosition) {
                        vh.mOpenInfoImg.setVisibility(View.VISIBLE);
                        vh.mInfoLayout.setVisibility(View.INVISIBLE);
                    }
                }
                vh = (MovieListAdapter.MovieVH) mDiscreteScrollView.getViewHolder(adapterPosition - 1);
                if (vh != null) {
                    vh.mNameText.setAlpha(0);

                    if (mPrePos != adapterPosition) {
                        vh.mOpenInfoImg.setVisibility(View.VISIBLE);
                        vh.mInfoLayout.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
        mPrePos = adapterPosition;
    }

    /**
     * 滑动监听
     *
     * @param scrollPosition  右滑 0 -- 1 左滑 -0 -- -1
     * @param currentPosition 当前adapter位置
     * @param newPosition     新的adapter位置
     * @param currentHolder
     * @param newCurrent
     */
    @Override
    public void onScroll(float scrollPosition, int currentPosition, int newPosition,
                         @Nullable MovieListAdapter.MovieVH currentHolder, @Nullable MovieListAdapter.MovieVH newCurrent) {
       // Log.i("zhangle15", "onScroll: " + scrollPosition);
        float position = Math.abs(scrollPosition);
        //bg1和bg2交替显示
        //偶数位置 bg2 奇数位置bg1
        boolean isOdd = currentPosition % 2 != 0;
        if (isOdd) {
            displayBgImage(currentPosition, mBgImg1);
            displayBgImage(newPosition, mBgImg2);
            mBgImg1.setAlpha(1 - position);
            mBgImg2.setAlpha(position);
        } else {
            displayBgImage(currentPosition, mBgImg2);
            displayBgImage(newPosition, mBgImg1);
            mBgImg1.setAlpha(position);
            mBgImg2.setAlpha(1 - position);
        }

        if (currentHolder != null && newCurrent != null) {
            float fastAlpha = position + 0.4f;
            currentHolder.mNameText.setAlpha(1 - fastAlpha);
            newCurrent.mNameText.setAlpha(fastAlpha);
            if (currentHolder.mInfoLayout.getVisibility() == View.VISIBLE) {
                currentHolder.mInfoLayout.setAlpha(1 - fastAlpha);
            }
        }
    }
}

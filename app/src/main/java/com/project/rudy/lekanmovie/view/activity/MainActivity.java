package com.project.rudy.lekanmovie.view.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.project.rudy.lekanmovie.view.adapter.TabPagerAdapter;
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

    @BindView(R.id.view_pager)
    UnScrollableViewPager mViewPager;
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.now_radio)
    RadioButton mNowBtn;
    @BindView(R.id.coming_radio)
    RadioButton mComingBtn;

    private FragmentManager mFragmentManager;
    private MovieListFragment mNowMovieListFragment;
    private MovieListFragment mComingMovieListFragment;
    private List<Fragment> mMovieListFragment;
    private MainActivityPresenterImpl mMainActivityPresenter;
    private TabPagerAdapter mTabPagerAdapter;


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
        ButterKnife.bind(this);

        initViewPagerFragment();

        initToolBar();
        initRadio();

    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     /*   toolbar.inflateMenu(R.menu.main);
         MenuItem itemSearch = toolbar.findViewById(R.id.action_search);
        MenuItem itemBoxOffice = toolbar.findViewById(R.id.action_box_office);*/
        setSupportActionBar(toolbar);
    }

    private void initRadio() {
        String font = "font.ttf";
        Typeface typeface = Typeface.createFromAsset(getAssets(), font);
        mNowBtn.setTypeface(typeface);
        mComingBtn.setTypeface(typeface);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position = checkedId == R.id.now_radio ? 0 : 1;
                if (mViewPager.getCurrentItem() == position)
                    return;

                mViewPager.setCurrentItem(position);
            }
        });
    }

    private void initViewPagerFragment() {
        mFragmentManager = getSupportFragmentManager();
        mMovieListFragment = new ArrayList<>();

        mNowMovieListFragment = MovieListFragment.newInstance(0);
        mComingMovieListFragment = MovieListFragment.newInstance(1);
        mMovieListFragment.add(mNowMovieListFragment);
        mMovieListFragment.add(mComingMovieListFragment);
        mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), mMovieListFragment);
        mTabPagerAdapter.setTabTitles(new String[]{getString(R.string.has_released), getString(R.string.going_to_release)});
        mViewPager.setAdapter(mTabPagerAdapter);
    }

    @Override
    public void setFragmentScrollViewAdapter(List<MovieData> movieData) {
//        Log.i("zhangle15", "setFragmentScrollViewAdapter: "+(int)((MovieListFragment) mFragmentManager.findFragmentById(0)).getArguments().get("id"));
        mNowMovieListFragment.setScrollViewAdapter(movieData.get(MOVIE_RELEASE).getData());
        mComingMovieListFragment.setScrollViewAdapter(movieData.get(MOVIE_WILL_RELEASE).getData());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}

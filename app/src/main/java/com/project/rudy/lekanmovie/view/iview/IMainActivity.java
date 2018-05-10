package com.project.rudy.lekanmovie.view.iview;

import com.project.rudy.lekanmovie.model.MovieData;

import java.util.List;

/**
 * Created by maoyan on 2018/5/7.
 */

public interface IMainActivity {
    void setFragmentScrollViewAdapter(List<MovieData> movieData);
}

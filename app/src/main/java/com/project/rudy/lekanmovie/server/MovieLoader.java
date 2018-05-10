package com.project.rudy.lekanmovie.server;

import com.project.rudy.lekanmovie.model.Movie;
import com.project.rudy.lekanmovie.model.MovieData;
import com.project.rudy.lekanmovie.model.MovieDataVO;
import com.project.rudy.lekanmovie.model.RequestResult;
import com.project.rudy.lekanmovie.server.api.MovieAPI;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by maoyan on 2018/5/6.
 */

public class MovieLoader extends ObjectLoader {

    private static MovieAPI mMovieAPI;

    private static MovieAPI getMovieApi() {
        if (mMovieAPI == null) {
            mMovieAPI = RetrofitServiceManager.getInstance().createService(MovieAPI.class);
        }
        return mMovieAPI;
    }

    /**
     * 获取上映和待映的影片信息
     *
     * @param city
     * @return
     */
    public static Observable<List<MovieData>> getMovieInfo(String city) {
        return observe(getMovieApi().getMovieInfo(APIConfigs.APPKEY_JUHE, city))
                .map(new Func1<RequestResult<MovieDataVO>, List<MovieData>>() {

                    @Override
                    public List<MovieData> call(RequestResult<MovieDataVO> movieDataVORequestResult) {
                        return movieDataVORequestResult.getResult().getData();
                    }
                });
    }

}

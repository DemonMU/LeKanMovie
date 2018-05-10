package com.project.rudy.lekanmovie.server.api;


import com.project.rudy.lekanmovie.model.MovieDataVO;
import com.project.rudy.lekanmovie.model.RequestResult;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by maoyan on 2018/5/3.
 */

public interface MovieAPI {
    @GET("onebox/movie/pmovie")
    Observable<RequestResult<MovieDataVO>> getMovieInfo(
            @Query("key") String key,
            @Query("city") String city
    );


}

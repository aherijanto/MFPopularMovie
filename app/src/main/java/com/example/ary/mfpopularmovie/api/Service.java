package com.example.ary.mfpopularmovie.api;

import com.example.ary.mfpopularmovie.model.MoviesResponse;
import com.example.ary.mfpopularmovie.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ary on 7/1/17.
 */
/** edit for resubmiting ---> from value---> public class Service, edit on Sunday,Jun2,2017*/
/** edit for resubmiting ---> removing return (interface method cannot have body) edit on Sunday,Jun2,2017*/

public interface Service {
    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);


    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer(@Path("movie_id") int movie_id, @Query("api_key") String apiKey);

}
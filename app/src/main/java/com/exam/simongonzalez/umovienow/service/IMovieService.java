package com.exam.simongonzalez.umovienow.service;

import com.exam.simongonzalez.umovienow.model.MovieData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by simongonzalez on 3/13/17.
 */

public interface IMovieService {

    @GET("movie/popular")
    Observable<MovieData> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("page") Integer page);

    @GET("movie/top_rated")
    Observable<MovieData> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("page") Integer page);

    @GET("movie/upcoming")
    Observable<MovieData> getUpcomingMovies(
            @Query("api_key") String apiKey,
            @Query("page") Integer page);

    @GET("search/movie")
    Observable<MovieData> getSearchedMovies(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") Integer page);

}

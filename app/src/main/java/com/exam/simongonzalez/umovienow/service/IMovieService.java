package com.exam.simongonzalez.umovienow.service;

import com.exam.simongonzalez.umovienow.model.Movie;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by simongonzalez on 3/13/17.
 */

public interface IMovieService {

    @GET("popular")
    Observable<List<Movie>> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("page") String page);

    @GET("top_rated")
    Observable<List<Movie>> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("page") String page);

    @GET("upcoming")
    Observable<List<Movie>> getUpcomingMovies(
            @Query("api_key") String apiKey,
            @Query("page") String page);

    @GET("search/movie")
    Observable<List<Movie>> getSearchedMovies(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") String page);

}

package com.exam.simongonzalez.umovienow.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.exam.simongonzalez.umovienow.model.Movie;
import com.exam.simongonzalez.umovienow.service.IMovieService;
import com.exam.simongonzalez.umovienow.service.MovieService;
import com.exam.simongonzalez.umovienow.view.IPopularView;

import java.util.List;
import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by simongonzalez on 10/03/17.
 */

public class PopularPresenter implements IPopularPresenter {

    private static String API_KEY = "367ef3f968fd589d24409b9240735c38";
    IPopularView iPopularView;
    Context context;
    MovieService movieService;
    IMovieService iMovieService;

    public PopularPresenter(IPopularView iPopularView, Context context) {
        this.iPopularView = iPopularView;
        this.context = context;
    }

    @Override
    public void loadPopularMovies(String page) {
        iMovieService = MovieService.getMovieApi().create(IMovieService.class);
        Call<Movie> call = (Call<Movie>) iMovieService.getPopularMovies(API_KEY, page);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.d("Response", "Exito");
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("Response", "Error");
            }
        });
    }
}

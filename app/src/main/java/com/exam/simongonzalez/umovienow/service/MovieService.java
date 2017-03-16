package com.exam.simongonzalez.umovienow.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by simongonzalez on 3/13/17.
 */

public class MovieService {

    private static String BASE_URL = "https://api.themoviedb.org/3/";

    public MovieService() {

    }

    public static Retrofit getMovieApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }
}

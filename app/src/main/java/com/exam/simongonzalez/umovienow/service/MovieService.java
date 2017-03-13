package com.exam.simongonzalez.umovienow.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by simongonzalez on 3/13/17.
 */

public class MovieService {

    private static String BASE_URL = "https://api.themoviedb.org/3/";
    private static String API_KEY = "367ef3f968fd589d24409b9240735c38";
    private IMovieService iMovieService;

    public MovieService() {

    }

    public static Retrofit getMovieApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}

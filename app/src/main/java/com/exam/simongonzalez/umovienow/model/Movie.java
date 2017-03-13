package com.exam.simongonzalez.umovienow.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by simongonzalez on 10/03/17.
 */

public class Movie {

    @SerializedName("original_title")
    String original_title;

    @SerializedName("original_language")
    String original_language;

    @SerializedName("release_date")
    String release_date;

    public Movie() {

    }

}

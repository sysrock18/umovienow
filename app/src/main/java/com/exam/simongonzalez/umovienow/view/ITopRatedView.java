package com.exam.simongonzalez.umovienow.view;

import com.exam.simongonzalez.umovienow.model.MovieData;

/**
 * Created by simongonzalez on 10/03/17.
 */

public interface ITopRatedView {

    void startAdapter(MovieData movieData);

    void setNextData(MovieData movieData);

}

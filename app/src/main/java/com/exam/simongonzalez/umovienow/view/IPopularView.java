package com.exam.simongonzalez.umovienow.view;

import com.exam.simongonzalez.umovienow.model.MovieData;

/**
 * Created by simongonzalez on 10/03/17.
 */

public interface IPopularView {

    void showMessage(String message);

    void startAdapter(MovieData movieData);

}

package com.exam.simongonzalez.umovienow;

import com.exam.simongonzalez.umovienow.model.MovieData;

/**
 * Created by simongonzalez on 3/15/17.
 */

public interface ISearchResultsView {

    void startAdapter(MovieData movieData);

    void setNextData(MovieData movieData);

}

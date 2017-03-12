package com.exam.simongonzalez.umovienow.presenter;

import android.content.Context;

import com.exam.simongonzalez.umovienow.view.ITopRatedView;

/**
 * Created by simongonzalez on 10/03/17.
 */

public class TopRatedPresenter implements ITopRatedPresenter {

    ITopRatedView iTopRatedView;
    Context context;

    public TopRatedPresenter(ITopRatedView iTopRatedView, Context context) {
        this.iTopRatedView = iTopRatedView;
        this.context = context;
    }

}

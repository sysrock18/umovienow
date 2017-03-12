package com.exam.simongonzalez.umovienow.presenter;

import android.content.Context;

import com.exam.simongonzalez.umovienow.view.IPopularView;

/**
 * Created by simongonzalez on 10/03/17.
 */

public class PopularPresenter implements IPopularPresenter {

    IPopularView iPopularView;
    Context context;

    public PopularPresenter(IPopularView iPopularView, Context context) {
        this.iPopularView = iPopularView;
        this.context = context;
    }

}

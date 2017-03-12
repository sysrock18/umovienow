package com.exam.simongonzalez.umovienow.presenter;

import android.content.Context;

import com.exam.simongonzalez.umovienow.view.IUpcomingView;

/**
 * Created by simongonzalez on 10/03/17.
 */

public class UpcomingPresenter implements IUpcomingPresenter {

    IUpcomingView iUpcomingView;
    Context context;

    public UpcomingPresenter(IUpcomingView iUpcomingView, Context context) {
        this.iUpcomingView = iUpcomingView;
        this.context = context;
    }

}

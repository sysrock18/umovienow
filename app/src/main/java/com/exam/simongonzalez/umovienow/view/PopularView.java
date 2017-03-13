package com.exam.simongonzalez.umovienow.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exam.simongonzalez.umovienow.MainActivity;
import com.exam.simongonzalez.umovienow.R;
import com.exam.simongonzalez.umovienow.presenter.IPopularPresenter;
import com.exam.simongonzalez.umovienow.presenter.PopularPresenter;

public class PopularView extends Fragment implements IPopularView {


    private IPopularPresenter iPopularPresenter;

    public PopularView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        iPopularPresenter = new PopularPresenter(this, getContext());
        iPopularPresenter.loadPopularMovies("1");

        return inflater.inflate(R.layout.fragment_popular_view, container, false);
    }

}

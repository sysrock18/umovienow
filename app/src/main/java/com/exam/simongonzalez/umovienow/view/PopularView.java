package com.exam.simongonzalez.umovienow.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.exam.simongonzalez.umovienow.R;
import com.exam.simongonzalez.umovienow.model.MovieData;
import com.exam.simongonzalez.umovienow.model.Result;
import com.exam.simongonzalez.umovienow.presenter.IPopularPresenter;
import com.exam.simongonzalez.umovienow.presenter.PopularPresenter;

import java.util.ArrayList;

public class PopularView extends Fragment implements IPopularView {


    private IPopularPresenter iPopularPresenter;
    private RecyclerView moviesList;

    public PopularView() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_popular_view, container, false);

        // Inflate the layout for this fragment
        iPopularPresenter = new PopularPresenter(this, getContext());
        iPopularPresenter.loadPopularMovies(1);

        moviesList = (RecyclerView) v.findViewById(R.id.rvPopular);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        moviesList.setLayoutManager(llm);

        return v;
    }

    @Override
    public void startAdapter(MovieData movieData) {
        ArrayList<Result> results = (ArrayList<Result>) movieData.getResults();
        MovieAdapter movieAdapter = new MovieAdapter(results, getActivity());
        moviesList.setAdapter(movieAdapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message,
                Toast.LENGTH_LONG).show();
    }

}

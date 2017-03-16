package com.exam.simongonzalez.umovienow.view;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import javax.inject.Inject;

public class PopularView extends Fragment implements IPopularView {


    private IPopularPresenter iPopularPresenter;
    private RecyclerView moviesList;
    int page = 1;
    ArrayList<Result> results = new ArrayList<Result>();

    @Inject
    MovieAdapter movieAdapter;

    public PopularView() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_popular_view, container, false);

        moviesList = (RecyclerView) v.findViewById(R.id.rvPopular);
        moviesList.setHasFixedSize(true);
        page = 1;

        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        moviesList.setLayoutManager(llm);

        iPopularPresenter = new PopularPresenter(this, getContext());
        iPopularPresenter.loadPopularMovies(page);

        return v;
    }

    @Override
    public void startAdapter(MovieData movieData) {
        results = (ArrayList<Result>) movieData.getResults();
        movieAdapter = new MovieAdapter(results, getActivity(), moviesList);

        movieAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                movieAdapter.addLoader();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        iPopularPresenter.loadPopularMovies(page);
                    }
                }, 1000);

            }
        });

        moviesList.setAdapter(movieAdapter);
    }

    @Override
    public void setNextData(MovieData movieData) {
        movieAdapter.setLoaded();
        movieAdapter.removeLoader();
        results = (ArrayList<Result>) movieData.getResults();
        movieAdapter.updateList(results);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message,
                Toast.LENGTH_LONG).show();
    }

}

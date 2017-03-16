package com.exam.simongonzalez.umovienow.view;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exam.simongonzalez.umovienow.R;
import com.exam.simongonzalez.umovienow.model.MovieData;
import com.exam.simongonzalez.umovienow.model.Result;
import com.exam.simongonzalez.umovienow.presenter.IUpcomingPresenter;
import com.exam.simongonzalez.umovienow.presenter.UpcomingPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

public class UpcomingView extends Fragment implements IUpcomingView {

    private IUpcomingPresenter iUpcomingPresenter;
    private RecyclerView moviesList;
    int page = 1;
    ArrayList<Result> results = new ArrayList<Result>();

    @Inject
    MovieAdapter movieAdapter;

    public UpcomingView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upcoming_view, container, false);

        moviesList = (RecyclerView) v.findViewById(R.id.rvUpcoming);
        page = 1;

        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        moviesList.setLayoutManager(llm);

        iUpcomingPresenter = new UpcomingPresenter(this, getContext());
        iUpcomingPresenter.loadUpcomingMovies(page);

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
                        iUpcomingPresenter.loadUpcomingMovies(page);
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
}

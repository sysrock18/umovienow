package com.exam.simongonzalez.umovienow.view;


import android.os.Bundle;
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
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
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

        // Inflate the layout for this fragment
        moviesList = (RecyclerView) v.findViewById(R.id.rvPopular);

        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        iPopularPresenter = new PopularPresenter(this, getContext());
        iPopularPresenter.loadPopularMovies(page);

        moviesList.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                loading = true;
                if(dy > 0)
                {
                    visibleItemCount = llm.getChildCount();
                    totalItemCount = llm.getItemCount();
                    pastVisiblesItems = llm.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            page++;
                            iPopularPresenter.loadPopularMovies(page);
                        }
                    }
                }
            }
        });

        moviesList.setLayoutManager(llm);

        return v;
    }

    @Override
    public void startAdapter(MovieData movieData) {
        results = (ArrayList<Result>) movieData.getResults();
        movieAdapter = new MovieAdapter(results, getActivity());
        moviesList.setAdapter(movieAdapter);
    }

    @Override
    public void setNextData(MovieData movieData) {
        results = (ArrayList<Result>) movieData.getResults();
        movieAdapter.updateList(results);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message,
                Toast.LENGTH_LONG).show();
    }

}

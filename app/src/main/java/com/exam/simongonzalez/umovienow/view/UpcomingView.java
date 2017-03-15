package com.exam.simongonzalez.umovienow.view;


import android.os.Bundle;
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
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
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

        iUpcomingPresenter = new UpcomingPresenter(this, getContext());
        iUpcomingPresenter.loadUpcomingMovies(page);

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
                            iUpcomingPresenter.loadUpcomingMovies(page);
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
}

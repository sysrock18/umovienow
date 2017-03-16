package com.exam.simongonzalez.umovienow;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.exam.simongonzalez.umovienow.model.MovieData;
import com.exam.simongonzalez.umovienow.model.Result;
import com.exam.simongonzalez.umovienow.presenter.ISearchResultsPresenter;
import com.exam.simongonzalez.umovienow.presenter.SearchResultsPresenter;
import com.exam.simongonzalez.umovienow.view.MovieAdapter;
import com.exam.simongonzalez.umovienow.view.OnLoadMoreListener;

import java.util.ArrayList;

import javax.inject.Inject;

public class SearchResultsView extends AppCompatActivity implements ISearchResultsView {

    private Toolbar toolbar;
    private ISearchResultsPresenter iSearchResultsPresenter;
    private RecyclerView searchList;
    int page = 1;
    ArrayList<Result> results = new ArrayList<Result>();
    String query = "";

    @Inject
    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(query);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            searchList = (RecyclerView) findViewById(R.id.rvSearch);
            page = 1;

            final LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);

            iSearchResultsPresenter = new SearchResultsPresenter(this, this);
            iSearchResultsPresenter.seachMovies(query, page);

            searchList.setLayoutManager(llm);

        }
    }

    @Override
    public void startAdapter(MovieData movieData) {
        results = (ArrayList<Result>) movieData.getResults();
        movieAdapter = new MovieAdapter(results, this, searchList);

        movieAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                movieAdapter.addLoader();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        iSearchResultsPresenter.seachMovies(query, page);
                    }
                }, 1000);

            }
        });

        searchList.setAdapter(movieAdapter);
    }

    @Override
    public void setNextData(MovieData movieData) {
        movieAdapter.setLoaded();
        movieAdapter.removeLoader();
        results = (ArrayList<Result>) movieData.getResults();
        movieAdapter.updateList(results);
    }
}

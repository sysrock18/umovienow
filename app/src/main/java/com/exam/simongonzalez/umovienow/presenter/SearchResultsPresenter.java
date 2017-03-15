package com.exam.simongonzalez.umovienow.presenter;

import android.content.Context;

import com.exam.simongonzalez.umovienow.ISearchResultsView;
import com.exam.simongonzalez.umovienow.model.MovieData;
import com.exam.simongonzalez.umovienow.service.IMovieService;
import com.exam.simongonzalez.umovienow.service.MovieService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by simongonzalez on 3/15/17.
 */

public class SearchResultsPresenter implements ISearchResultsPresenter {

    private static String API_KEY = "367ef3f968fd589d24409b9240735c38";
    ISearchResultsView iSearchResultsView;
    Context context;
    IMovieService iMovieService;

    public SearchResultsPresenter(ISearchResultsView iSearchResultsView, Context context) {
        this.iSearchResultsView = iSearchResultsView;
        this.context = context;
    }

    @Override
    public void seachMovies(String query, final Integer page) {
        MovieService movieService = new MovieService();
        iMovieService = movieService.getMovieApi().create(IMovieService.class);

        iMovieService.getSearchedMovies(API_KEY, query, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieData value) {
                        if(page > 1) {
                            iSearchResultsView.setNextData(value);
                        } else {
                            iSearchResultsView.startAdapter(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

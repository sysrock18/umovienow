package com.exam.simongonzalez.umovienow.presenter;

import android.content.Context;

import com.exam.simongonzalez.umovienow.model.MovieData;
import com.exam.simongonzalez.umovienow.service.IMovieService;
import com.exam.simongonzalez.umovienow.service.MovieService;
import com.exam.simongonzalez.umovienow.view.IPopularView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by simongonzalez on 10/03/17.
 */

public class PopularPresenter implements IPopularPresenter {

    private static String API_KEY = "367ef3f968fd589d24409b9240735c38";
    IPopularView iPopularView;
    Context context;
    IMovieService iMovieService;

    public PopularPresenter(IPopularView iPopularView, Context context) {
        this.iPopularView = iPopularView;
        this.context = context;
    }

    @Override
    public void loadPopularMovies(Integer page) {

        MovieService movieService = new MovieService();
        iMovieService = movieService.getMovieApi().create(IMovieService.class);

        iMovieService.getPopularMovies(API_KEY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        iPopularView.showMessage("onSubscribe");
                    }

                    @Override
                    public void onNext(MovieData value) {
                        iPopularView.showMessage("onNext");
                        iPopularView.startAdapter(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        iPopularView.showMessage("onError");
                    }

                    @Override
                    public void onComplete() {
                        iPopularView.showMessage("onComplete");
                    }
                });
    }
}

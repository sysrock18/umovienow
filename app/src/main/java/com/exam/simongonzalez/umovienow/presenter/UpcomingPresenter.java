package com.exam.simongonzalez.umovienow.presenter;

import android.content.Context;

import com.exam.simongonzalez.umovienow.model.MovieData;
import com.exam.simongonzalez.umovienow.service.IMovieService;
import com.exam.simongonzalez.umovienow.service.MovieService;
import com.exam.simongonzalez.umovienow.view.IUpcomingView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by simongonzalez on 10/03/17.
 */

public class UpcomingPresenter implements IUpcomingPresenter {

    private static String API_KEY = "367ef3f968fd589d24409b9240735c38";
    IUpcomingView iUpcomingView;
    Context context;
    IMovieService iMovieService;

    public UpcomingPresenter(IUpcomingView iUpcomingView, Context context) {
        this.iUpcomingView = iUpcomingView;
        this.context = context;
    }

    @Override
    public void loadUpcomingMovies(final Integer page) {
        MovieService movieService = new MovieService();
        iMovieService = movieService.getMovieApi().create(IMovieService.class);

        iMovieService.getUpcomingMovies(API_KEY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieData value) {
                        if(page > 1) {
                            iUpcomingView.setNextData(value);
                        } else {
                            iUpcomingView.startAdapter(value);
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

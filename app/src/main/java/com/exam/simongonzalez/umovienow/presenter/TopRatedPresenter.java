package com.exam.simongonzalez.umovienow.presenter;

import android.content.Context;

import com.exam.simongonzalez.umovienow.model.MovieData;
import com.exam.simongonzalez.umovienow.service.IMovieService;
import com.exam.simongonzalez.umovienow.service.MovieService;
import com.exam.simongonzalez.umovienow.view.ITopRatedView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by simongonzalez on 10/03/17.
 */

public class TopRatedPresenter implements ITopRatedPresenter {

    private static String API_KEY = "367ef3f968fd589d24409b9240735c38";
    ITopRatedView iTopRatedView;
    Context context;
    IMovieService iMovieService;

    public TopRatedPresenter(ITopRatedView iTopRatedView, Context context) {
        this.iTopRatedView = iTopRatedView;
        this.context = context;
    }

    @Override
    public void loadTopRatedMovies(final Integer page) {
        MovieService movieService = new MovieService();
        iMovieService = movieService.getMovieApi().create(IMovieService.class);

        iMovieService.getTopRatedMovies(API_KEY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieData value) {
                        if(page > 1) {
                            iTopRatedView.setNextData(value);
                        } else {
                            iTopRatedView.startAdapter(value);
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

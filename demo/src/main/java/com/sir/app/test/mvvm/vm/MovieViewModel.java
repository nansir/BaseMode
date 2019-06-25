package com.sir.app.test.mvvm.vm;

import android.app.Application;
import android.support.annotation.NonNull;

import com.sir.app.test.mvvm.contract.MovieContract;
import com.sir.app.test.mvvm.model.source.MovieRepository;
import com.sir.library.mvvm.base.BaseViewModel;

/**
 * 视图模型
 * Created by zhuyinan on 2019/6/24.
 */
public class MovieViewModel extends BaseViewModel<MovieRepository> implements MovieContract {

    public MovieViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void getMovie(String city) {
        mRepository.getMovie(city);
    }
}

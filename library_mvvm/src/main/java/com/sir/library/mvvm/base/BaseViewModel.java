package com.sir.library.mvvm.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sir.library.mvvm.ContractProxy;
import com.sir.library.retrofit.event.LiveBus;


/**
 * 基本视图模型
 * Created by zhuyinan on 2019/6/21.
 */
public class BaseViewModel<T extends BaseRepository> extends AndroidViewModel {

    public T mRepository;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mRepository = ContractProxy.getNewInstance(this, 0);
    }

    public <T> MutableLiveData<T> subscribe(Object eventKey, Class<T> tClass) {
        return LiveBus.getDefault().subscribe(eventKey, tClass);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRepository != null) {
            mRepository.unDisposable();
        }
    }
}

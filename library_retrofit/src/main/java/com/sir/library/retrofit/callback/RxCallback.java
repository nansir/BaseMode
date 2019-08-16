package com.sir.library.retrofit.callback;

import com.sir.library.retrofit.exception.ResponseThrowable;
import com.sir.library.retrofit.exception.ServerException;
import com.sir.library.retrofit.response.HttpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhuyinan on 2019/7/2.
 */
public abstract class RxCallback<T> implements Callback<HttpResponse<T>> {

    @Override
    public void onResponse(Call<HttpResponse<T>> call, Response<HttpResponse<T>> res) {
        HttpResponse<T> response = res.body();
        if (response.getResCode() != 0) {
            ServerException resultException = new ServerException(response.getResMsg(), response.getResCode());
            ResponseThrowable ex = new ResponseThrowable(resultException, resultException.code);
            ex.message = resultException.message;
            onFailure(ex);
        } else {
            onSuccess(response.getResBody());
        }
    }

    @Override
    public void onFailure(Call<HttpResponse<T>> call, Throwable t) {
        if (t instanceof ResponseThrowable) {
            onFailure((ResponseThrowable) t);
        } else if (t instanceof ResponseThrowable) {
            onFailure(new ResponseThrowable(t, 1000));
        }
    }

    /**
     * 错误回调
     */
    protected abstract void onFailure(ResponseThrowable ex);

    /**
     * 请求成功
     *
     * @param t
     */
    protected abstract void onSuccess(T t);

}

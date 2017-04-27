package com.example.willy.rxjava2.retrofit.transformer;

import com.example.willy.rxjava2.retrofit.response.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import retrofit2.Response;

/**
 * Created by willy on 2017/4/27.
 */

public class DefaultTransformer<T extends Response<R>, R extends BaseResponse>
        implements ObservableTransformer<T, R> {

    @Override
    public ObservableSource<R> apply(Observable<T> upstream) {
        return upstream
                .compose(new SchedulerTransformer<T>())
                .compose(new ErrorCheckTransformer<T, R>());
    }
}

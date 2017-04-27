package com.example.willy.rxjava2.retrofit.transformer;

import android.text.TextUtils;

import com.example.willy.rxjava2.retrofit.ErrorResponseException;
import com.example.willy.rxjava2.retrofit.response.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * Created by willy on 2017/4/27.
 */

public class ErrorCheckTransformer<T extends Response<R>, R extends BaseResponse>
        implements ObservableTransformer<T, R> {

    public static final String DEFAULT_ERROR_MESSAGE = "There's something went wrong!";

    @Override
    public ObservableSource<R> apply(Observable<T> upstream) {
        return upstream.map(new Function<T, R>() {
            @Override
            public R apply(T t) throws Exception {
                if (t.isSuccessful() && t.errorBody() == null && t.code() == BaseResponse.SUCCESS_CODE) {
                    return t.body();
                }

                String errorMsg = DEFAULT_ERROR_MESSAGE;
                if (!TextUtils.isEmpty(t.message())) {
                    errorMsg = t.message();
                }
                throw new ErrorResponseException(errorMsg);
            }
        });
    }
}

package com.example.willy.rxjava2.retrofit;

import android.os.Bundle;

import com.example.willy.rxjava2.BaseActivity;
import com.example.willy.rxjava2.R;
import com.example.willy.rxjava2.retrofit.response.PostResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        ServiceGenerator.createService(ApiService.class)
                .getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Response<List<PostResponse>>, List<PostResponse>>() {
                    @Override
                    public List<PostResponse> apply(Response<List<PostResponse>> listResponse) throws Exception {
                        return listResponse.body();
                    }
                })
                .flatMap(new Function<List<PostResponse>, ObservableSource<PostResponse>>() {
                    @Override
                    public ObservableSource<PostResponse> apply(List<PostResponse> postResponses) throws Exception {
                        return Observable.fromIterable(postResponses);
                    }
                })
                .subscribe(new Observer<PostResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostResponse postResponse) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
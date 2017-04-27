package com.example.willy.rxjava2.retrofit;

import android.os.Bundle;

import com.example.willy.rxjava2.BaseActivity;
import com.example.willy.rxjava2.R;
import com.example.willy.rxjava2.retrofit.object.User;
import com.example.willy.rxjava2.retrofit.response.UserResponse;
import com.example.willy.rxjava2.retrofit.transformer.DefaultTransformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import retrofit2.Response;

public class DemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        ServiceGenerator.createService(ApiService.class).getUsers()
                .compose(new DefaultTransformer<Response<UserResponse>, UserResponse>())
                .flatMap(new Function<UserResponse, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(UserResponse userResponse) throws Exception {
                        return Observable.fromIterable(userResponse.getUsers());
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {

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
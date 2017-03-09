package com.example.willy.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AsyncActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskSubscribeOnNewThread();
    }

    private void defaultUsage() {
        // Consumer 是 Observer 的一種，但他只處理 onNext 訊息，其他訊息都會忽略 (onError, onComplete))
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("test", "Observer thread is : " + Thread.currentThread().getName());
            }
        };

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e("test", "Observable thread is : " + Thread.currentThread().getName());

                emitter.onNext("on");
                emitter.onComplete();
            }
        });

        // observable 和 observer 預設都會跑在 main thread 上
        observable.subscribe(consumer);
    }

    private void taskSubscribeOnNewThread() {
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("test", "Observer thread is : " + Thread.currentThread().getName());
            }
        };

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e("test", "Observable thread is : " + Thread.currentThread().getName());

                emitter.onNext("on");
                emitter.onComplete();
            }
        });

        observable
                //  'subscribeOn' only the first time is work
                .subscribeOn(Schedulers.newThread())
                //  this line will not work
                .subscribeOn(AndroidSchedulers.mainThread())

                //  'observeOn' can be called several times
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }
}

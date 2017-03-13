package com.example.willy.rxjava2;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AsyncActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        taskSubscribeOnNewThread();
    }

    private void defaultUsage() {
        /*  Consumer 是 Observer 的一種，但他只處理 onNext 訊息，其他訊息都會忽略 (onError, onComplete)
            如果只想要處理收到的訊息時，就可以使用Consumer
         */
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "Observer thread is : " + Thread.currentThread().getName());
            }
        };

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG, "Observable thread is : " + Thread.currentThread().getName());

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
                Log.e(TAG, "Observer thread is : " + Thread.currentThread().getName());
            }
        };

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG, "Observable thread is : " + Thread.currentThread().getName());

                emitter.onNext("on");
                emitter.onComplete();
            }
        });

        observable
                //  'subscribeOn' 改變 Observer（開關按鈕：發送事件） 的 thread，只有第一次是有用的
                .subscribeOn(Schedulers.newThread())
                //  第二次之後的 'subscribeOn' 都不會發生效用
                .subscribeOn(AndroidSchedulers.mainThread())
                //  'observeOn' 改變 Observable（檯燈：接收事件） 的 thread，可以被呼叫多次，並且會在下一行開始有效
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "After observeOn main thread : " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "After observeOn io thread : " + Thread.currentThread().getName());
                    }
                })
                .subscribe(consumer);
    }
}

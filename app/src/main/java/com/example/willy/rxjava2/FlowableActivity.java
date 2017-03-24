package com.example.willy.rxjava2;

import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FlowableActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        inSynchronized();
//        inAsynchronized();
    }

    /**
     * If you don't tell the Subscriber how many requests you can handle,
     * It will throws an 'MissingBackpressureException' error.
     */
    private void inSynchronized() {
        Subscriber<String> subscriber = new Subscriber<String>() {
            Subscription mSubscription;

            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe");
                mSubscription = s;
                // Tell Subscriber that how many requests you can handle .
                mSubscription.request(1);
            }

            @Override
            public void onNext(String string) {
                Log.d(TAG, "onNext: " + string);
                // When we processed one message, request one more message.
                mSubscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "onError:" + t.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                emitter.onNext("on");
                Log.d(TAG, "emit: " + "on");
                emitter.onNext("off");
                Log.d(TAG, "emit: " + "off");
                emitter.onNext("on");
                Log.d(TAG, "emit: " + "on");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);

        flowable.subscribe(subscriber);
    }

    /**
     * If you don't tell the Subscriber how many requests you can handle,
     * It will NOT throws an 'MissingBackpressureException' error.
     * Because Flowable has a default buffer that size is 128,
     * those sended messages will put into this buffer until you request!
     * <p>
     * You can also try to modify the number 128 in for loop to 129 to see the error message.
     */
    private void inAsynchronized() {
        Subscriber<String> subscriber = new Subscriber<String>() {
            Subscription mSubscription;

            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe");
                mSubscription = s;
            }

            @Override
            public void onNext(String string) {
                Log.d(TAG, "onNext: " + string);
            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "onError:" + t.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                for (int i = 0; i < 128; i++) {
                    emitter.onNext("on");
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);

        flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }
}
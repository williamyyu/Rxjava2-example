package com.example.willy.rxjava2;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NormalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 想像是檯燈：接收來自開關按鈕的訊息 */
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String string) {
                // 收到 開/關 的訊息
                Log.e(TAG, "onNext: " + string);
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError:" + t.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        };

        /* 想像是開關按鈕：發送 開/關 的訊息給檯燈 */
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                // 使用 ObservableEmitter 來發送訊息
                emitter.onNext("on");
                emitter.onNext("off");
                emitter.onNext("on");
                emitter.onComplete();
            }
        });
        
        // 把檯燈跟開關按鈕串連起來
        observable.subscribe(observer);

        // 簡單的寫法，會自動呼叫onComplete
        Observable.just("on", "off", "on").subscribe(observer);
    }
}

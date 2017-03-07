package com.example.willy.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DisposableActivity extends AppCompatActivity {

    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCompositeDisposable = new CompositeDisposable();

        /* 想像是檯燈：接收來自開關按鈕的訊息 */
        Observer<String> observer = new Observer<String>() {

            /*
                調用dispose後，會切斷檯燈與開關按鈕的聯繫
                但開關按鈕仍會繼續發送訊息，只是檯燈接收不到
             */
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.e("test", "onSubscribe");
                mDisposable = d;

                // 可以把所有 Disposable 新增到 CompositeDisposable，以便一次全部 dispose
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(String string) {
                // 收到 開/關 的訊息
                Log.e("test", "onNext: " + string);

                // 收到 off 的訊息後切斷與開關按鈕的聯繫
                if (string.equals("off")) {
                    mDisposable.dispose();
                    Log.e("test", "isDisposed : " + mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.e("test", "onError:" + t.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Log.e("test", "onComplete");
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 一次切斷所有聯繫
        mCompositeDisposable.clear();
    }
}

package com.example.willy.rxjava2.operators;

import android.os.Bundle;
import android.util.Log;

import com.example.willy.rxjava2.BaseActivity;
import com.example.willy.rxjava2.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "onNext: " + s);
            }
        };

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                //  假設開關按鈕傳送來的訊息變成數字： 0(off) 和 1(on)
                emitter.onNext(1);
                emitter.onNext(0);
                emitter.onNext(1);
                emitter.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                //  我們可以透過 map 在這邊將訊息轉換成任何想要顯示的格式
                if (integer.intValue() == 0) {
                    return "off";
                } else {
                    return "on";
                }
            }
        }).subscribe(consumer);
    }
}

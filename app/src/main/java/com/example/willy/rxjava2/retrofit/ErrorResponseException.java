package com.example.willy.rxjava2.retrofit;

import java.io.IOException;

/**
 * Created by willy on 2017/3/27.
 */

public class ErrorResponseException extends IOException {
    public ErrorResponseException() {
        super();
    }

    public ErrorResponseException(String detailMessage) {
        super(detailMessage);
    }
}

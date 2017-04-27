package com.example.willy.rxjava2.retrofit.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by willy on 2017/3/27.
 */

public class BaseResponse {
    public static final int SUCCESS_CODE = 200;

    private String mMessage;
    private int mCode;

    @SerializedName("error_response")
    private ErrorResponse mErrorResponse;

    private static class ErrorResponse {
        private String mMessage;
        private int mCode;
    }
}

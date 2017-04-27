package com.example.willy.rxjava2.retrofit;

import com.example.willy.rxjava2.retrofit.response.UserResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by willy on 2017/3/27.
 */

public interface ApiService {

    @GET("users")
    Observable<Response<UserResponse>> getUsers();
}

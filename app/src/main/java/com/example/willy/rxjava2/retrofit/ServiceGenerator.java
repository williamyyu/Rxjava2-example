package com.example.willy.rxjava2.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by willy on 2017/4/26.
 */

public class ServiceGenerator {

    private static final String BASE_URL = "https://reqres.in/api/";

    private static final OkHttpClient sOkHttpClient = new OkHttpClient.Builder()
//            .addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request original = chain.request();
//
//                    // Request customization: add request headers
//                    Request.Builder requestBuilder = original.newBuilder()
//                            .header("Accept", "application/json")
//                            .method(original.method(), original.body());
//
//                    Request request = requestBuilder.build();
//                    return chain.proceed(request);
//                }
//            })
            .build();

    public static <T> T createService(Class<T> serviceClazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(sOkHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClazz);
    }
}

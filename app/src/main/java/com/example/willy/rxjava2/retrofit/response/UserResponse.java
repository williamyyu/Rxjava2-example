package com.example.willy.rxjava2.retrofit.response;

import com.example.willy.rxjava2.retrofit.object.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by willy on 2017/4/26.
 */

public class UserResponse extends BaseResponse {
    @SerializedName("page")
    private int mPage;

    @SerializedName("per_page")
    private int mPerPpage;

    @SerializedName("total")
    private int mTotal;

    @SerializedName("total_pages")
    private int mToalPages;

    @SerializedName("data")
    private List<User> mUsers;

    public int getPage() {
        return mPage;
    }

    public int getPerPpage() {
        return mPerPpage;
    }

    public int getTotal() {
        return mTotal;
    }

    public int getToalPages() {
        return mToalPages;
    }

    public List<User> getUsers() {
        return mUsers;
    }
}

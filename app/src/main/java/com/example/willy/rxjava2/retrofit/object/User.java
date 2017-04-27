package com.example.willy.rxjava2.retrofit.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by willy on 2017/4/27.
 */

public class User {
    @SerializedName("id")
    private int mId;

    @SerializedName("first_name")
    private String mFirstName;

    @SerializedName("last_name")
    private String mLastName;

    @SerializedName("avatar")
    private String mAvatar;

    public int getId() {
        return mId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getAvatar() {
        return mAvatar;
    }
}

package com.example.willy.rxjava2.test_objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willy on 2017/3/16.
 */

public class Student {

    private int mId;
    private String mName;
    private List<Course> mCourseList;

    public Student(int id, String name) {
        mId = id;
        mName = name;
        mCourseList = new ArrayList<>();

        for (int i = 1; i < 4; i++) {
            Course course = new Course(mId);
            mCourseList.add(course);
        }
    }

    public String getName() {
        return mName;
    }

    public List<Course> getCourseList() {
        return mCourseList;
    }
}

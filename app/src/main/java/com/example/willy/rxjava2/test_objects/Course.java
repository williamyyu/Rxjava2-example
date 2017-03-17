package com.example.willy.rxjava2.test_objects;

import java.util.Random;

/**
 * Created by willy on 2017/3/16.
 */

public class Course {

    private static final String[] COURSE_NAME = {"Math", "Science", "History"};

    private int mStudentId;
    private String mCourseName;

    public Course(int studentId) {
        mStudentId = studentId;
        mCourseName = COURSE_NAME[randomNumber(0, 2)];
    }

    public int getStudentId() {
        return mStudentId;
    }

    public String getCourseName() {
        return mCourseName;
    }

    private int randomNumber(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}

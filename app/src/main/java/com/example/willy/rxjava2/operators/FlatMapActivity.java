package com.example.willy.rxjava2.operators;

import android.os.Bundle;
import android.util.Log;

import com.example.willy.rxjava2.BaseActivity;
import com.example.willy.rxjava2.R;
import com.example.willy.rxjava2.test_objects.Course;
import com.example.willy.rxjava2.test_objects.Student;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class FlatMapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        /*
            假設我們現在有三筆學生(Student)的資料（David, Willy, Debby）以及他們所修課程的清單(CourseList)
            目標：印出每個學生的修課清單
         */

//        useMap();
        useFlatMap();
//        useConcatMap();
    }

    private void useMap() {
        /*
            使用 map 來達到這件事情的流程：
                1. 發出三筆 Student 的訊息
                2. 使用 map 將訊息內容從 Student 轉成 CourseList
                3. 收到訊息後，跑 for loop 印出所有 Course name

            會發現最後一步要多跑一個 for loop 其實有點麻煩
         */
        Consumer<List<Course>> consumer = new Consumer<List<Course>>() {
            @Override
            public void accept(List<Course> courseList) throws Exception {
                // 收到訊息後，跑 for loop 印出所有 Course name
                for (int i = 0; i < courseList.size(); i++) {
                    Course course = courseList.get(i);
                    Log.d(TAG, "    Course name: " + course.getCourseName());
                }
            }
        };

        Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(ObservableEmitter<Student> emitter) throws Exception {
                // 發送三筆 Student 資料
                emitter.onNext(new Student(1, "David"));
                emitter.onNext(new Student(2, "Willy"));
                emitter.onNext(new Student(3, "Debby"));
                emitter.onComplete();
            }
        }).map(new Function<Student, List<Course>>() {
            @Override
            public List<Course> apply(Student student) throws Exception {
                // 將訊息內容從 Student 轉為 CourseList
                return student.getCourseList();
            }
        }).subscribe(consumer);
    }

    private void useFlatMap() {
        Consumer<Course> consumer = new Consumer<Course>() {
            @Override
            public void accept(Course course) throws Exception {
                /*
                    因為要看出 flatMap 是無序的發送訊息，所以在 Course name 後面多印出 Course 屬於哪個 Student Id.
                    透過 Log message 會看到收到訊息的順序沒有依照原先發送的順序
                    如果要有序的發送訊息，使用 concatMap operator
                */
                Log.d(TAG, "    Course name: " + course.getCourseName() + "(" + course.getStudentId() + ")");
            }
        };

        Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(ObservableEmitter<Student> emitter) throws Exception {
                emitter.onNext(new Student(1, "David"));
                emitter.onNext(new Student(2, "Willy"));
                emitter.onNext(new Student(3, "Debby"));
                emitter.onComplete();
            }
        }).flatMap(new Function<Student, ObservableSource<Course>>() {
            @Override
            public ObservableSource<Course> apply(Student student) throws Exception {
                Log.d(TAG, "Student name: " + student.getName());
                return Observable.fromIterable(student.getCourseList()).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(consumer);
    }

    private void useConcatMap() {
        Consumer<Course> consumer = new Consumer<Course>() {
            @Override
            public void accept(Course course) throws Exception {
                Log.d(TAG, "    Course name: " + course.getCourseName() + "(" + course.getStudentId() + ")");
            }
        };

        Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(ObservableEmitter<Student> emitter) throws Exception {
                emitter.onNext(new Student(1, "David"));
                emitter.onNext(new Student(2, "Willy"));
                emitter.onNext(new Student(3, "Debby"));
                emitter.onComplete();
            }
        }).concatMap(new Function<Student, ObservableSource<Course>>() {
            @Override
            public ObservableSource<Course> apply(Student student) throws Exception {
                Log.d(TAG, "Student name: " + student.getName());
                return Observable.fromIterable(student.getCourseList()).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(consumer);
    }
}

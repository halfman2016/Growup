package com.yper.feng.growup;

import android.app.Application;

import com.yper.feng.growup.Module.Log;
import com.yper.feng.growup.Module.Teacher;

import java.util.HashMap;

/**
 * Created by Feng on 2016/7/21.
 */
public class MyApplication extends Application {
    public static String Url = "http://lizhibutian.boteteam.com/";
    private static MyApplication instance;
    private static String username;
    private static Log log;
    private static Teacher teacher;
    // test 和 release 2 种模式
    private static String mode = "release";
    private HashMap<String, Integer> defaultValues;

    public static Teacher getTeacher() {
        return teacher;
    }

    public static void setTeacher(Teacher teacher) {
        MyApplication.teacher = teacher;
    }

    public static MyApplication getInstance(){
        return instance;
    }

    public static String getMode() {
        return mode;
    }

    public static void setMode(String mode) {
        MyApplication.mode = mode;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        MyApplication.username = username;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        defaultValues = new HashMap<>();
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public HashMap<String, Integer> getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(HashMap<String, Integer> defaultValues) {
        this.defaultValues = defaultValues;
    }
}

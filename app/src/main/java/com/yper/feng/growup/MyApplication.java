package com.yper.feng.growup;

import android.app.Application;

import com.yper.feng.growup.Module.Log;
import com.yper.feng.growup.Module.Teacher;

import java.security.PublicKey;
import java.util.HashMap;

/**
 * Created by Feng on 2016/7/21.
 */
public class MyApplication extends Application {
    private  HashMap<String,Integer> defaultValues;
    private static MyApplication instance;
    private static String username;
    private static Log log;
    private static Teacher teacher;
    public static String Url="http://lizhibutian.boteteam.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        defaultValues=new HashMap<>();
    }

    public static Teacher getTeacher() {
        return teacher;
    }

    public static void setTeacher(Teacher teacher) {
        MyApplication.teacher = teacher;
    }

    public void setLog(Log log){this.log=log;}
    public Log getLog(){return log;}

    public static MyApplication getInstance(){
        return instance;
    }

    public HashMap<String, Integer> getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(HashMap<String, Integer> defaultValues) {
        this.defaultValues = defaultValues;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        MyApplication.username = username;
    }
}

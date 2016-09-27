package com.yper.feng.growup.Util;

import android.os.Handler;

import java.util.HashMap;

/**
 * Created by Feng on 2016/7/20.
 */
public class ActionDefaultValue {

    //单例模式

    private int  version;
    private static final HashMap<String,Integer> defaultValues =new HashMap<>();
    private String Whos; //谁的默认分值,其实就是规则名称
    private static final ActionDefaultValue actionDefaultValue=new ActionDefaultValue();


    public static ActionDefaultValue getInstance() {

         return actionDefaultValue;
    }

    private ActionDefaultValue() {


    }

    public void loadDefaultValue()
    {
        //从本地json分值系统，如果本地没有，则从网络读取
        //所以初始该对象应该是在异步线程中
//        if(isExistValueFile())
//        {
//            readFromFile();
//        }
//
//        else
//        {
//            new Thread(){
//                /**
//                 * Calls the <code>run()</code> method of the Runnable object the receiver
//                 * holds. If no Runnable is set, does nothing.
//                 *
//                 * @see Thread#start
//                 */
//                @Override
//                public void run() {
//                    super.run();
//                }
//            }.start();
//
//        }
    }

    public HashMap<String,Integer> readFromFile(){
        HashMap<String,Integer> maps=new HashMap<>();
        //maps.put("打扫卫生",2);

        return maps;
    }
    public void addNewActionDefaultValue(String actionName, int defaultValue)
   {



    }



}

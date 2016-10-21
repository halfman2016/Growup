package com.yper.feng.growup.Module;

/**
 * Created by jiangfeng on 2016/10/15.
 */

public class DayChecksAcion extends BaseAction{
    //每日检查项目的样表的实体，按类别选出来

    private int defaultvalue;
    private boolean dayaddscore=false; //每日自动加分，如果为true，每日自动加分。加分时机每日最新登录就加分。或者每日晚上23：00，自动加分。

    public DayChecksAcion(String actionName, String actionType,int defaultvalue) {
        super(actionName, actionType);
        this.defaultvalue=defaultvalue;
    }

    public int getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(int defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    public boolean isDayaddscore() {
        return dayaddscore;
    }

    public void setDayaddscore(boolean dayaddscore) {
        this.dayaddscore = dayaddscore;
    }
}

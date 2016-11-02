package com.yper.feng.growup.Module;

/**
 * Created by jiangfeng on 2016/10/21.
 */

public class DayCheckListAction extends BaseAction{
    private int defaultvalue;
    //defaultvalue 如果是>0，每人都是自动加分的；
    //defaultvalue如果<0，那么个别人减分，其他人都是0；
    private boolean dayaddscore=false; //每日自动加分，如果为true，每日自动加分。加分时机每日最新登录就加分。或者每日晚上23：00，自动加分。


//每天实际按类别存放的检查项目


    public DayCheckListAction(String actionName, String actionType, int defaultvalue) {
        super(actionName, actionType);
        this.defaultvalue = defaultvalue;

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

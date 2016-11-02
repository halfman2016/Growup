package com.yper.feng.growup.Module;

/**
 * Created by jiangfeng on 2016/10/15.
 */

public class PinAction extends ScoreAction {
    //标准行为库的实体

    int goodvalue;
    int badvalue;


    public PinAction(String actionName, String actionType, int actionScoreValue,int goodvalue,int badvalue) {
        super(actionName, actionType, actionScoreValue);
        this.goodvalue=goodvalue;
        this.badvalue=badvalue;
    }

    public int getGoodvalue() {
        return goodvalue;
    }

    public void setGoodvalue(int goodvalue) {
        this.goodvalue = goodvalue;
    }

    public int getBadvalue() {
        return badvalue;
    }

    public void setBadvalue(int badvalue) {
        this.badvalue = badvalue;
    }
}

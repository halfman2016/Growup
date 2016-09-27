package com.yper.feng.growup.Module;

/**
 * Created by Feng on 2016/6/16.
 */
public class actionTag {
    private String actionTagName; //行为名称
    private int actionDefaultValue; //行为的默认分值
    private String actionRelationPeople;
    private String actionPegPeople;
    private String actionRealtionPhoto;
    private String actionType; //行为类别

    public actionTag(String actionTagName) {
        this.actionTagName = actionTagName;
    }
}

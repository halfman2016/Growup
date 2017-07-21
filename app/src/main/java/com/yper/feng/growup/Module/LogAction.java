package com.yper.feng.growup.Module;

import java.sql.Timestamp;

/**
 * Created by Feng on 2017/6/21.
 */

public class LogAction {

    private String actionname;
    private Timestamp actiontime;
    private String actionpeoplename;
    private String actionpeopleid;
    private String actiongradeclassname;

    public String getActionname() {
        return actionname;
    }

    public void setActionname(String actionname) {
        this.actionname = actionname;
    }

    public Timestamp getActiontime() {
        return actiontime;
    }

    public void setActiontime(Timestamp actiontime) {
        this.actiontime = actiontime;
    }

    public String getActionpeoplename() {
        return actionpeoplename;
    }

    public void setActionpeoplename(String actionpeoplename) {
        this.actionpeoplename = actionpeoplename;
    }

    public String getActionpeopleid() {
        return actionpeopleid;
    }

    public void setActionpeopleid(String actionpeopleid) {
        this.actionpeopleid = actionpeopleid;
    }

    public String getActiongradeclassname() {
        return actiongradeclassname;
    }

    public void setActiongradeclassname(String actiongradeclassname) {
        this.actiongradeclassname = actiongradeclassname;
    }
}

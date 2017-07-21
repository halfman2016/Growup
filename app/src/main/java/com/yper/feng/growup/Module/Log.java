package com.yper.feng.growup.Module;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by Feng on 2017/6/15.
 */

public class Log {
    private String lid; // login in id
    private String lname;
    private String  gradeClassName;
    private UUID uuid;
    private Timestamp stime;
    private Timestamp etime;
    private long  dtime;

    public long getDtime() {
        return dtime;
    }

    public void setDtime(long dtime) {
        this.dtime = dtime;
    }

    public Log(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getgradeClassName() {
        return gradeClassName;
    }

    public void setgradeClassName(String gradeClassName) {
        this.gradeClassName = gradeClassName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Timestamp getStime() {
        return stime;
    }

    public void setStime(Timestamp stime) {
        this.stime = stime;
    }

    public Timestamp getEtime() {
        return etime;
    }

    public void setEtime(Timestamp etime) {
        this.etime = etime;
    }
}

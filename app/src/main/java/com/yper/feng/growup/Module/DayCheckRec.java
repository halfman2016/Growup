package com.yper.feng.growup.Module;

import java.util.List;
import java.util.UUID;

/**
 * Created by jiangfeng on 2016/11/3.
 */

public class DayCheckRec {
  private   String strdate;
  private   List<DayCommonAction>  dayCommonActions;
 private    List<Student> students;
    private  String typename;
    private    String checkedteachername;
private     UUID checkedteacherid;

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    private GradeClass gradeClass;

    public GradeClass getGradeClass() {
        return gradeClass;
    }

    public void setGradeClass(GradeClass gradeClass) {
        this.gradeClass = gradeClass;
    }

    public DayCheckRec(){

    }
    public DayCheckRec(List<DayCommonAction> dayCommonActions,GradeClass gradeClass,String typename, String strdate) {
        this.dayCommonActions = dayCommonActions;
        this.gradeClass=gradeClass;
        this.strdate = strdate;
        this.typename=typename;
    }

    public String getCheckedteachername() {
        return checkedteachername;
    }

    public void setCheckedteachername(String checkedteachername) {
        this.checkedteachername = checkedteachername;
    }

    public UUID getCheckedteacherid() {
        return checkedteacherid;
    }

    public void setCheckedteacherid(UUID checkedteacherid) {
        this.checkedteacherid = checkedteacherid;
    }

    public String getStrdate() {
        return strdate;
    }

    public void setStrdate(String strdate) {
        this.strdate = strdate;
    }

    public List<DayCommonAction> getDayCommonActions() {
        return dayCommonActions;
    }

    public void setDayCommonActions(List<DayCommonAction> dayCommonActions) {
        this.dayCommonActions = dayCommonActions;
    }
}

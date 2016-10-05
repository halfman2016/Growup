package com.yper.feng.growup.Module;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Feng on 2016/6/16.
 */
public class Student extends People {
    private String rankname; //级别，称号
    private int score;  //分数
    private String gradeclass;
    private UUID gradeclassid;
    ArrayList<UUID> scoreActionsUUID = new ArrayList<>(); //相关行文记录集合 UUID

    private String QQ;

    public Student(String name) {
        super(name);
    }


    public String getRankname() {
        return rankname;
    }

    public void setRankname(String rankname) {
        this.rankname = rankname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGradeclass() {
        return gradeclass;
    }

    public void setGradeclass(String gradeclass) {
        this.gradeclass = gradeclass;
    }

    public UUID getGradeclassid() {
        return gradeclassid;
    }

    public void setGradeclassid(UUID gradeclassid) {
        this.gradeclassid = gradeclassid;
    }

    public ArrayList<UUID> getScoreActionsUUID() {
        return scoreActionsUUID;
    }

    public void setScoreActionsUUID(ArrayList<UUID> scoreActionsUUID) {
        this.scoreActionsUUID = scoreActionsUUID;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }
}

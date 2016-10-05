package com.yper.feng.growup.Module;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Feng on 2016/6/21.
 */
public class Photopic {
    private Date photodate; //拍照时间
    private int takePhotoPid; //拍照的人ID
    private String photoauthor;
    private String photomemo;
    private String photopic; //图片地址
    private String belongToSubject; // 照片属于哪个专题
    private int haspeged; //0从未标定，1 标定了，n 标定了几次
    private UUID _id=UUID.randomUUID();

    public UUID get_id() {
        return _id;
    }

    public Date getPhotodate() {
        return photodate;
    }

    public void setPhotodate(Date photodate) {
        this.photodate = photodate;
    }

    public int getTakePhotoPid() {
        return takePhotoPid;
    }

    public void setTakePhotoPid(int takePhotoPid) {
        this.takePhotoPid = takePhotoPid;
    }

    public String getPhotoauthor() {
        return photoauthor;
    }

    public void setPhotoauthor(String photoauthor) {
        this.photoauthor = photoauthor;
    }

    public String getPhotomemo() {
        return photomemo;
    }

    public void setPhotomemo(String photomemo) {
        this.photomemo = photomemo;
    }

    public String getPhotopic() {
        return photopic;
    }

    public void setPhotopic(String photopic) {
        this.photopic = photopic;
    }

    public String getBelongToSubject() {
        return belongToSubject;
    }

    public void setBelongToSubject(String belongToSubject) {
        this.belongToSubject = belongToSubject;
    }

    public int getHaspeged() {
        return haspeged;
    }

    public void setHaspeged(int haspeged) {
        this.haspeged = haspeged;
    }
}

package com.yper.feng.growup.Module;

import java.util.UUID;

/**
 * Created by Feng on 2016/9/25.
 */
public class PicPinAction extends ScoreAction {
    private UUID picsrcid;
//针对某个照片的具体标定内容
    public PicPinAction(String actionName, String actionType, int actionScoreValue) {
        super(actionName, actionType, actionScoreValue);
    }

    public UUID getPicsrcid() {
        return picsrcid;
    }

    public void setPicsrcid(UUID picsrcid) {
        this.picsrcid = picsrcid;
    }
}

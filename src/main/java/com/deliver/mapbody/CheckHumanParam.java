package com.deliver.mapbody;

import java.util.List;

/**
 * Created by pdl on 2018/9/27.
 */
public class CheckHumanParam {
    List<Integer> humanID;
    Integer checkFlag;
    List<String> remarks;

    public List<Integer> getHumanID() {
        return humanID;
    }

    public void setHumanID(List<Integer> humanID) {
        this.humanID = humanID;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }

    public List<String> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<String> remarks) {
        this.remarks = remarks;
    }
}

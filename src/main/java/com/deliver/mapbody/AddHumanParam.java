package com.deliver.mapbody;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pdl on 2018/9/28.
 */
public class AddHumanParam {
    Integer humanID;
    Integer humanType;
    String humanName;
    String password;
    Integer gradeID;
    Integer classID;
    Integer schoolID;
    String img;
    String tel;
    float[] feature;
    Integer phoneFlag;
    Integer managerType;
    List<Integer> relHumanlistID;
    String remarks;
    Integer newFlag;
    String parentTel;
    String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentTel() {
        return parentTel;
    }

    public void setParentTel(String parentTel) {
        this.parentTel = parentTel;
    }

    public Integer getHumanID() {
        return humanID;
    }

    public void setHumanID(Integer humanID) {
        this.humanID = humanID;
    }

    public Integer getHumanType() {
        return humanType;
    }

    public void setHumanType(Integer humanType) {
        this.humanType = humanType;
    }

    public String getHumanName() {
        return humanName;
    }

    public void setHumanName(String humanName) {
        this.humanName = humanName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGradeID() {
        return gradeID;
    }

    public void setGradeID(Integer gradeID) {
        this.gradeID = gradeID;
    }

    public Integer getClassID() {
        return classID;
    }

    public void setClassID(Integer classID) {
        this.classID = classID;
    }

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public float[] getFeature() {
        return feature;
    }

    public void setFeature(float[] feature) {
        this.feature = feature;
    }

    public Integer getPhoneFlag() {
        return phoneFlag;
    }

    public void setPhoneFlag(Integer phoneFlag) {
        this.phoneFlag = phoneFlag;
    }

    public Integer getManagerType() {
        return managerType;
    }

    public void setManagerType(Integer managerType) {
        this.managerType = managerType;
    }

    public List<Integer> getRelHumanlistID() {
        return relHumanlistID;
    }

    public void setRelHumanlistID(List<Integer> relHumanlistID) {
        this.relHumanlistID = relHumanlistID;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(Integer newFlag) {
        this.newFlag = newFlag;
    }

    @Override
    public String toString() {
        return "AddHumanParam{" +
                "humanID=" + humanID +
                ", humanType=" + humanType +
                ", humanName='" + humanName + '\'' +
                ", password='" + password + '\'' +
                ", gradeID=" + gradeID +
                ", classID=" + classID +
                ", schoolID=" + schoolID +
                ", img='" + img + '\'' +
                ", tel='" + tel + '\'' +
                ", feature=" + Arrays.toString(feature) +
                ", phoneFlag=" + phoneFlag +
                ", managerType=" + managerType +
                ", relHumanlistID=" + relHumanlistID +
                ", remarks='" + remarks + '\'' +
                ", newFlag=" + newFlag +
                ", parentTel='" + parentTel + '\'' +
                ", parentName='" + parentName + '\'' +
                '}';
    }
}

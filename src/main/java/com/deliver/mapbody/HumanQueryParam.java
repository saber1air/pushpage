package com.deliver.mapbody;

/**
 * Created by pdl on 2018/9/25.
 */
public class HumanQueryParam {
    private String humanName;
    private Integer schoolID;
    private Integer gradeID;
    private Integer classID;
    private String tel;
    private Integer humanType;
    private Integer atschoolFlag;
    private Integer checkFlag;
    private Integer managerFlag;
    private String beginTime;
    private String endTime;

    public String getHumanName() {
        return humanName;
    }

    public void setHumanName(String humanName) {
        this.humanName = humanName;
    }

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getHumanType() {
        return humanType;
    }

    public void setHumanType(Integer humanType) {
        this.humanType = humanType;
    }

    public Integer getAtschoolFlag() {
        return atschoolFlag;
    }

    public void setAtschoolFlag(Integer atschoolFlag) {
        this.atschoolFlag = atschoolFlag;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Integer getManagerFlag() {
        return managerFlag;
    }

    public void setManagerFlag(Integer managerFlag) {
        this.managerFlag = managerFlag;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

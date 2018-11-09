package com.deliver.entity;

import com.deliver.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by pdl on 2018/9/13.
 */

@Entity
@Table(name="tc_human_info")
public class HumanInfo extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer humanID;

    @Column(length = 20,nullable=true)
    private String humanName;
    @Column(nullable=true)
    private Integer gradeID;
    @Column(nullable=true)
    private Integer classID;
    @Column(nullable=true)
    private Date createTime;
    @Column(columnDefinition="int default 0",nullable=true)
    private Integer atschoolFlag=0;
    @Column(length = 20,nullable=true)
    private String tel;
    @Column(columnDefinition="int default 0",nullable=true)
    private Integer deleteFlag=0;
    @Column(nullable=true)
    private Date updateTime;
    @Column(nullable=true)
    private Integer humanType=-1;
    @Column(length = 20,nullable=true)
    private String idnum;
    @Column(nullable=true)
    private Integer schoolID;
    @Column(nullable=true)
    private String remarks;
    @Column(columnDefinition="int default 0",nullable=true)
    private Integer online=0;
    @Column(columnDefinition="int default -1",nullable=true)
    private Integer managerType=-1;
    @Column(length = 40,nullable=true)
    private String password;
    @Column(columnDefinition="int default -1",nullable=true)
    private Integer checkFlag=-1;

    @Column(columnDefinition="int default -1",nullable=true)
    private Integer applayAuth=-1;

    @Column(columnDefinition="int default -1",nullable=true)
    private Integer managerFlag=-1;

    @Column(length = 40,nullable=true)
    private String token;
    @Column(length = 40,nullable=true)
    private String clientID;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public Integer getApplayAuth() {
        return applayAuth;
    }

    public void setApplayAuth(Integer applayAuth) {
        this.applayAuth = applayAuth;
    }

    public Integer getHumanID() {
        return humanID;
    }

    public void setHumanID(Integer humanID) {
        this.humanID = humanID;
    }

    public String getHumanName() {
        return humanName;
    }

    public void setHumanName(String humanName) {
        this.humanName = humanName;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAtschoolFlag() {
        return atschoolFlag;
    }

    public void setAtschoolFlag(Integer atschoolFlag) {
        this.atschoolFlag = atschoolFlag;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getHumanType() {
        return humanType;
    }

    public void setHumanType(Integer humanType) {
        this.humanType = humanType;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Integer getManagerType() {
        return managerType;
    }

    public void setManagerType(Integer managerType) {
        this.managerType = managerType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "HumanInfo{" +
                "humanID=" + humanID +
                ", humanName='" + humanName + '\'' +
                ", gradeID=" + gradeID +
                ", classID=" + classID +
                ", createTime=" + createTime +
                ", atschoolFlag=" + atschoolFlag +
                ", tel='" + tel + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", updateTime=" + updateTime +
                ", humanType=" + humanType +
                ", idnum='" + idnum + '\'' +
                ", schoolID=" + schoolID +
                ", remarks='" + remarks + '\'' +
                ", online=" + online +
                ", managerType=" + managerType +
                ", password='" + password + '\'' +
                ", checkFlag=" + checkFlag +
                ", applayAuth=" + applayAuth +
                ", managerFlag=" + managerFlag +
                ", token='" + token + '\'' +
                ", clientID='" + clientID + '\'' +
                '}';
    }
}

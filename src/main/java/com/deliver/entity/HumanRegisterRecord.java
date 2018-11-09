package com.deliver.entity;

import com.deliver.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by pdl on 2018/9/13.
 */

@Entity
@Table(name="tc_human_register_record")
public class HumanRegisterRecord extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer registerID;

    @Column(columnDefinition="int default -1",nullable=true)
    private Integer operateHumID=-1;
    @Column(nullable=true)
    private Integer regHumID;

    @Column(nullable=true)
    private Date createTime;

    @Column(nullable=true)
    private Date checkTime;

    @Column(columnDefinition="int default 0",nullable=true)
    private Integer checkFlag=0;
    @Column(columnDefinition="int default 0",nullable=true)
    private Integer deleteFlag=0;
    @Column(nullable=true)
    private String remarks;

    @Column(nullable=true)
    private Integer classID;

    @Column(nullable=true)
    private Integer schoolID;

    public Integer getRegisterID() {
        return registerID;
    }

    public void setRegisterID(Integer registerID) {
        this.registerID = registerID;
    }

    public Integer getOperateHumID() {
        return operateHumID;
    }

    public void setOperateHumID(Integer operateHumID) {
        this.operateHumID = operateHumID;
    }

    public Integer getRegHumID() {
        return regHumID;
    }

    public void setRegHumID(Integer regHumID) {
        this.regHumID = regHumID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    @Override
    public String toString() {
        return "HumanRegisterRecord{" +
                "registerID=" + registerID +
                ", operateHumID=" + operateHumID +
                ", regHumID=" + regHumID +
                ", createTime=" + createTime +
                ", checkTime=" + checkTime +
                ", checkFlag=" + checkFlag +
                ", deleteFlag=" + deleteFlag +
                ", remarks='" + remarks + '\'' +
                ", classID=" + classID +
                ", schoolID=" + schoolID +
                '}';
    }
}

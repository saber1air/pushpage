package com.deliver.entity;

import com.deliver.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by pdl on 2018/9/13.
 */

@Entity
@Table(name="tc_manager_register_record")
public class ManagerRegisterRecord extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer registerID;
    @Column(nullable=true)
    private Integer operateHumID;
    @Column(nullable=true)
    private Integer regManagerID;

    @Column(nullable=true)
    private Date createTime;

    @Column(nullable=true)
    private Date checkTime;

    @Column(nullable=true)
    private Integer applayAuth;
    @Column(columnDefinition="int default 0",nullable=true)
    private Integer checkFlag=0;
    @Column(columnDefinition="int default 0",nullable=true)
    private Integer deleteFlag=0;
    @Column(nullable=true)
    private String remarks;

    private Integer classID;

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

    public Integer getRegManagerID() {
        return regManagerID;
    }

    public void setRegManagerID(Integer regManagerID) {
        this.regManagerID = regManagerID;
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

    public Integer getApplayAuth() {
        return applayAuth;
    }

    public void setApplayAuth(Integer applayAuth) {
        this.applayAuth = applayAuth;
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

    @Override
    public String toString() {
        return "ManagerRegisterRecord{" +
                "registerID=" + registerID +
                ", operateHumID=" + operateHumID +
                ", regManagerID=" + regManagerID +
                ", createTime=" + createTime +
                ", checkTime=" + checkTime +
                ", applayAuth=" + applayAuth +
                ", checkFlag=" + checkFlag +
                ", deleteFlag=" + deleteFlag +
                ", remarks='" + remarks + '\'' +
                ", classID=" + classID +
                '}';
    }
}

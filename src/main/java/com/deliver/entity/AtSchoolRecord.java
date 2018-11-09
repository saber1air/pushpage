package com.deliver.entity;

import com.deliver.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by pdl on 2018/9/13.
 */

@Entity
@Table(name="tc_at_school_record")
public class AtSchoolRecord extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable=true)
    private Integer humanID;

    @Column(columnDefinition="int default 1",nullable=true)
    private Integer accessType=1;

    @Column(nullable=true)
    private Date accessTime;
    @Column(length = 100,nullable=true)
    private String message;
    @Column(nullable=true)
    private Integer checkresult;
    @Column(columnDefinition="int default -1",nullable=true)
    private Integer operateID=-1;
    @Column(columnDefinition="int default 0",nullable=true)
    private Integer deleteFlag=0;
    @Column(nullable=true)
    private String remarks;

    @Column(nullable=true)
    private Integer schoolID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHumanID() {
        return humanID;
    }

    public void setHumanID(Integer humanID) {
        this.humanID = humanID;
    }

    public Integer getAccessType() {
        return accessType;
    }

    public void setAccessType(Integer accessType) {
        this.accessType = accessType;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCheckresult() {
        return checkresult;
    }

    public void setCheckresult(Integer checkresult) {
        this.checkresult = checkresult;
    }

    public Integer getOperateID() {
        return operateID;
    }

    public void setOperateID(Integer operateID) {
        this.operateID = operateID;
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

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }

    @Override
    public String toString() {
        return "AtSchoolRecord{" +
                "id=" + id +
                ", humanID=" + humanID +
                ", accessType=" + accessType +
                ", accessTime=" + accessTime +
                ", message='" + message + '\'' +
                ", checkresult=" + checkresult +
                ", operateID=" + operateID +
                ", deleteFlag=" + deleteFlag +
                ", remarks='" + remarks + '\'' +
                ", schoolID=" + schoolID +
                '}';
    }
}

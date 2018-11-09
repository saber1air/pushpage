package com.deliver.entity;

import com.deliver.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by pdl on 2018/9/13.
 */

@Entity
@Table(name="tc_getui_record")
public class GeTueRecord extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer getuiID;

    @Column(nullable=true)
    private Integer studentID;
    @Column(nullable=true)
    private Integer parentID;

    @Column(nullable=true)
    private Date getuiTime;
    @Column(length = 100,nullable=true)
    private String message;

    @Column(columnDefinition="int default 0",nullable=true)
    private Integer deleteFlag=0;
    @Column(nullable=true)
    private String remarks;

    @Column(nullable=true)
    private Integer schoolID;

    @Column(columnDefinition="int default 0",nullable=true)
    private Integer accessType=0;

    public Integer getGetuiID() {
        return getuiID;
    }

    public void setGetuiID(Integer getuiID) {
        this.getuiID = getuiID;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public Date getGetuiTime() {
        return getuiTime;
    }

    public void setGetuiTime(Date getuiTime) {
        this.getuiTime = getuiTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Integer getAccessType() {
        return accessType;
    }

    public void setAccessType(Integer accessType) {
        this.accessType = accessType;
    }

    @Override
    public String toString() {
        return "GeTueRecord{" +
                "getuiID=" + getuiID +
                ", studentID=" + studentID +
                ", parentID=" + parentID +
                ", getuiTime=" + getuiTime +
                ", message='" + message + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", remarks='" + remarks + '\'' +
                ", schoolID=" + schoolID +
                ", accessType=" + accessType +
                '}';
    }
}

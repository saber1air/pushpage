package com.deliver.entity;

import com.deliver.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by pdl on 2018/9/13.
 */

@Entity
@Table(name="tc_deliver_record")
public class DeliverRecord extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer deliverID;

    @Column(nullable=true)
    private Integer studentID;
    @Column(nullable=true)
    private Integer parentID;

    @Column(length = 3000,nullable=true)
    private String media; //  现场照

    @Column(columnDefinition="int default 1",nullable=true)
    private Integer deliverType=1;

    @Column(columnDefinition="int default 1",nullable=true)
    private Integer accessType=1;

    @Column(nullable=true)
    private Date deliverTime;
    @Column(length = 100,nullable=true)
    private String message;
    @Column(columnDefinition="int default 1",nullable=true)
    private Integer checkresult=1;
    @Column(columnDefinition="int default -1",nullable=true)
    private Integer operateID=-1;
    @Column(columnDefinition="int default 0",nullable=true)
    private Integer deleteFlag=0;
    @Column(nullable=true)
    private String remarks;

    @Column(nullable=true)
    private Integer schoolID;

    public Integer getDeliverID() {
        return deliverID;
    }

    public void setDeliverID(Integer deliverID) {
        this.deliverID = deliverID;
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

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public Integer getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(Integer deliverType) {
        this.deliverType = deliverType;
    }

    public Integer getAccessType() {
        return accessType;
    }

    public void setAccessType(Integer accessType) {
        this.accessType = accessType;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
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
        return "DeliverRecord{" +
                "deliverID=" + deliverID +
                ", studentID=" + studentID +
                ", parentID=" + parentID +
                ", media='" + media + '\'' +
                ", deliverType=" + deliverType +
                ", accessType=" + accessType +
                ", deliverTime=" + deliverTime +
                ", message='" + message + '\'' +
                ", checkresult=" + checkresult +
                ", operateID=" + operateID +
                ", deleteFlag=" + deleteFlag +
                ", remarks='" + remarks + '\'' +
                ", schoolID=" + schoolID +
                '}';
    }
}

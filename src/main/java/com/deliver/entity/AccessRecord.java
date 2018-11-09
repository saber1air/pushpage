package com.deliver.entity;

import com.deliver.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by pdl on 2018/9/13.
 */

@Entity
@Table(name="tc_access_record")
public class AccessRecord extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accessID;

    @Column(nullable=true)
    private Integer humanID;

    @Column(length = 3000)
    private String media; //现场照

    @Column(columnDefinition="int default 1",nullable=false)
    private Integer accessType;

    @Column(nullable = true)
    private Date accessTime;
    @Column(length = 100,nullable = true)
    private String message;
    @Column(columnDefinition="int default 1",nullable=false)
    private Integer checkresult;
    @Column(columnDefinition="int default -1",nullable=false)
    private Integer operateID;
    @Column(columnDefinition="int default 0",nullable=false)
    private Integer deleteFlag;
    @Column(nullable = true)
    private String remarks;

    public Integer getAccessID() {
        return accessID;
    }

    public void setAccessID(Integer accessID) {
        this.accessID = accessID;
    }

    public Integer getHumanID() {
        return humanID;
    }

    public void setHumanID(Integer humanID) {
        this.humanID = humanID;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
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

    @Override
    public String toString() {
        return "AccessRecord{" +
                "accessID=" + accessID +
                ", humanID=" + humanID +
                ", media='" + media + '\'' +
                ", accessType=" + accessType +
                ", accessTime=" + accessTime +
                ", message='" + message + '\'' +
                ", checkresult=" + checkresult +
                ", operateID=" + operateID +
                ", deleteFlag=" + deleteFlag +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}

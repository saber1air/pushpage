package com.deliver.entity;

import com.deliver.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by pdl on 2018/9/13.
 */

@Entity
@Table(name="tc_version_info")
public class VersionInfo extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer versionID;

    @Column(length = 20,nullable=true)
    private String versionNum;

    @Column(length = 20,nullable=true)
    private String appID;

    @Column(nullable=true)
    private Date createTime;

    @Column(length = 20,nullable=true)
    private String versionUrl;

    @Column(columnDefinition="int default 0",nullable=true)
    private Integer deleteFlag=0;

    @Column(length = 100,nullable=true)
    private String remarks;

    @Column(length = 40,nullable=true)
    private String os;

    @Column(columnDefinition="int default 1",nullable=true)
    private Integer updateFlag=1;

    public Integer getVersionID() {
        return versionID;
    }

    public void setVersionID(Integer versionID) {
        this.versionID = versionID;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
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

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Integer getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(Integer updateFlag) {
        this.updateFlag = updateFlag;
    }

    @Override
    public String toString() {
        return "VersionInfo{" +
                "versionID=" + versionID +
                ", versionNum='" + versionNum + '\'' +
                ", appID='" + appID + '\'' +
                ", createTime=" + createTime +
                ", versionUrl='" + versionUrl + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", remarks='" + remarks + '\'' +
                ", os='" + os + '\'' +
                ", updateFlag=" + updateFlag +
                '}';
    }
}

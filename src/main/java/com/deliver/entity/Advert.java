package com.deliver.entity;

import com.deliver.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */

@Entity
@Table(name="tc_advert")
public class Advert extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer advertID;

    @Column(length = 40,nullable=true)
    private String advertName;

    @Column(length = 255,nullable=true)
    private String advertPath;

    @Column(nullable=true)
    private Integer schoolID;

    @Column(nullable=true)
    private Date createTime;
    @Column(nullable=true)
    private Date updateTime;
    @Column(columnDefinition="int default 0",nullable=true)
    private Integer deleteFlag=0;
    @Column(nullable=true)
    private String remarks;

    public Integer getAdvertID() {
        return advertID;
    }

    public void setAdvertID(Integer advertID) {
        this.advertID = advertID;
    }

    public String getAdvertName() {
        return advertName;
    }

    public void setAdvertName(String advertName) {
        this.advertName = advertName;
    }

    public String getAdvertPath() {
        return advertPath;
    }

    public void setAdvertPath(String advertPath) {
        this.advertPath = advertPath;
    }

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        return "Advert{" +
                "advertID=" + advertID +
                ", advertName='" + advertName + '\'' +
                ", advertPath='" + advertPath + '\'' +
                ", schoolID=" + schoolID +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}

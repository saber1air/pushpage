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
@Table(name="tc_school")
public class School extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer schoolID;

    @Column(length = 40,nullable=true)
    private String schoolName;

    @Column(nullable=true)
    private Integer regionID;

    @Column(length = 40,nullable=true)
    private String president; //校长

    @Column(nullable=true)
    private Date createTime;
    @Column(nullable=true)
    private Date updateTime;
    @Column(columnDefinition="int default 0",nullable=true)
    private Integer deleteFlag=0;
    @Column(nullable=true)
    private String remarks;
    @Column(length = 80,nullable=true)
    private String address;


    @Transient
    public List<GradeInfo> gradeInfo;

    @Transient
    public List<MacInfo> macInfo;

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getRegionID() {
        return regionID;
    }

    public void setRegionID(Integer regionID) {
        this.regionID = regionID;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<GradeInfo> getGradeInfo() {
        return gradeInfo;
    }

    public void setGradeInfo(List<GradeInfo> gradeInfo) {
        this.gradeInfo = gradeInfo;
    }

    public List<MacInfo> getMacInfo() {
        return macInfo;
    }

    public void setMacInfo(List<MacInfo> macInfo) {
        this.macInfo = macInfo;
    }

    @Override
    public String toString() {
        return "School{" +
                "schoolID=" + schoolID +
                ", schoolName='" + schoolName + '\'' +
                ", regionID=" + regionID +
                ", president='" + president + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                ", remarks='" + remarks + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

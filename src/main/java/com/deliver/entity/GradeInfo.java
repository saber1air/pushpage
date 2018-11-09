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
@Table(name="tc_grade_info")
public class GradeInfo extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer gradeID;

    @Column(nullable=true)
    private Integer gradeNum;
    @Column(length = 20,nullable=true)
    private String gradeName;
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


    @Transient
    private List<ClassInfo> classInfoList;

    public Integer getGradeID() {
        return gradeID;
    }

    public void setGradeID(Integer gradeID) {
        this.gradeID = gradeID;
    }

    public Integer getGradeNum() {
        return gradeNum;
    }

    public void setGradeNum(Integer gradeNum) {
        this.gradeNum = gradeNum;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
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

    public List<ClassInfo> getClassInfoList() {
        return classInfoList;
    }

    public void setClassInfoList(List<ClassInfo> classInfoList) {
        this.classInfoList = classInfoList;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gradeID=" + gradeID +
                ", gradeNum=" + gradeNum +
                ", gradeName='" + gradeName + '\'' +
                ", schoolID=" + schoolID +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}

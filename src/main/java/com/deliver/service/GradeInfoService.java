package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.GradeInfoDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.GradeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class GradeInfoService {
    @Autowired
    private GradeInfoDao gradeInfoDao;

    public List<GradeInfo> findBySchoolID(int schoolID){
        return gradeInfoDao.findBySchoolIDAndDeleteFlag(schoolID, 0);
    }

    public List<GradeInfo> findBySchoolIDonly(int schoolID){
        return gradeInfoDao.findBySchoolID(schoolID);

    }

    public List<GradeInfo> findBySchoolIDAndUpdateTimeAfter(int schoolID, Date date){
        return gradeInfoDao.findBySchoolIDAndUpdateTimeAfter(schoolID, date);
    }

    public GradeInfo findByGradeID(int id) {
        return gradeInfoDao.findByGradeIDAndDeleteFlag(id,0);
    }

    public GradeInfo save(GradeInfo gradeInfo) {
        gradeInfo.setCreateTime(new Date());
        gradeInfo.setUpdateTime(new Date());
        return gradeInfoDao.save(gradeInfo);
    }

    public List<GradeInfo> findBySchoolIDAndGradeNumAndDeleteFlag(int schoolID,int gradeNum, int delete){
        return gradeInfoDao.findBySchoolIDAndGradeNumAndDeleteFlag(schoolID,gradeNum,delete);
    }

    public GradeInfo update(GradeInfo gradeInfo) {
        gradeInfo.setUpdateTime(new Date());
        return gradeInfoDao.save(gradeInfo);
    }

    public int delete(int id) {
        return gradeInfoDao.deleteByGradeID(id);
    }

}

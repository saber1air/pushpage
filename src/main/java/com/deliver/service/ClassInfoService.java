package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.ClassInfoDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.ClassInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class ClassInfoService {
    @Autowired
    private ClassInfoDao classInfoDao;

    public ClassInfo findByClassID(int id) {
        return classInfoDao.findByClassIDAndDeleteFlag(id,0);
    }


    public List<ClassInfo> findBySchoolID(int id) {
        return classInfoDao.findBySchoolID(id);
    }

    public List<ClassInfo> findBySchoolIDAndUpdateTimeAfter(int id, Date date) {
        return classInfoDao.findBySchoolIDAndUpdateTimeAfter(id, date);

    }



    public List<ClassInfo> findByGradeID(int gradeID){
        return classInfoDao.findByGradeIDAndDeleteFlag(gradeID,0);
    }


    public ClassInfo save(ClassInfo classInfo) {
        classInfo.setCreateTime(new Date());
        classInfo.setUpdateTime(new Date());
        return classInfoDao.save(classInfo);
    }

    public List<ClassInfo> findBySchoolIDAndGradeIDAndClassNumAndDeleteFlag(int schoolID,int gradeID,int classNum,int deleteFlag){
        return classInfoDao.findBySchoolIDAndGradeIDAndClassNumAndDeleteFlag(schoolID,gradeID,classNum,deleteFlag);
    }

    public ClassInfo update(ClassInfo classInfo) {
        classInfo.setUpdateTime(new Date());
        return classInfoDao.save(classInfo);
    }

    public int delete(int id) {
        return classInfoDao.deleteByClassID(id);
    }

}

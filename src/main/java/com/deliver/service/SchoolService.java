package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.SchoolDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class SchoolService {
    @Autowired
    private SchoolDao schoolDao;

    public School findBySchoolIDAndDeleteFlag(int id,int deleteFlag) {
        return schoolDao.findBySchoolIDAndDeleteFlag(id,deleteFlag);
    }

    public School findBySchoolID(int id) {
        return schoolDao.findBySchoolID(id);
    }

    public List<School> findByDeleteFlag(int delete){
        return schoolDao.findByDeleteFlag(delete);
    }

    public School findBySchoolIDAndUpdateTimeAfter(int id, Date date) {
        return schoolDao.findBySchoolIDAndUpdateTimeAfter(id, date);

    }

    public School save(School school) {
        school.setCreateTime(new Date());
        school.setUpdateTime(new Date());
        return schoolDao.save(school);
    }

    public List<School> findBySchoolNameAndDeleteFlag(String schoolName,int deleteFlag) {
        return schoolDao.findBySchoolNameAndDeleteFlag(schoolName,deleteFlag);
    }

    public School update(School school) {
        school.setUpdateTime(new Date());
        return schoolDao.save(school);
    }

    public int delete(int id) {
        return schoolDao.deleteBySchoolID(id);
    }

}

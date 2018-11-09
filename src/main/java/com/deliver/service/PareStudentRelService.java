package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.PareStudentRelDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.ParenStudentRel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class PareStudentRelService {
    @Autowired
    private PareStudentRelDao pareStudentRelDao;

    public ParenStudentRel findByRelid(int id) {
        return pareStudentRelDao.findByRelid(id);
    }

    public List<ParenStudentRel> findByHumanIDAndUpdateTimeAfter(int id, Date date) {
        return pareStudentRelDao.findByHumanIDAndUpdateTimeAfterAndDeleteFlag(id, date, 0);
    }

    public List<ParenStudentRel> findBySchoolIDAndUpdateTimeAfter(int id, Date date) {
        return pareStudentRelDao.findBySchoolIDAndUpdateTimeAfterAndCheckFlag(id, date,1);

    }

    public List<ParenStudentRel> findBySchoolID(int id) {
        return pareStudentRelDao.findBySchoolIDAndCheckFlag(id,1);
    }


    public ParenStudentRel save(ParenStudentRel pareStudentRel) {
        return pareStudentRelDao.save(pareStudentRel);
    }

    public int delete(int id) {
        return pareStudentRelDao.deleteByRelid(id);
    }

    public List<ParenStudentRel> findByHomeIDAndHumanID(int parentID, int studentID){
        return pareStudentRelDao.findByHomeIDAndHumanID(parentID,studentID);
    }


    public List<ParenStudentRel> findByHomeIDAndHumanIDAndSchoolID(int parentID, int studentID,int schoolID){
        return pareStudentRelDao.findByHomeIDAndHumanIDAndSchoolID(parentID,studentID,schoolID);
    }

    public List<ParenStudentRel> findByHumanIDAndCheckFlagAndDeleteFlag(int id,int checkFlag,int deleteflag){
        return pareStudentRelDao.findByHumanIDAndCheckFlagAndDeleteFlag(id,checkFlag,deleteflag);
    }

    public ParenStudentRel addHumanRel(ParenStudentRel parenStudentRel){
        parenStudentRel.setCreateTime(new Date());
        parenStudentRel.setUpdateTime(new Date());
        return pareStudentRelDao.save(parenStudentRel);
    }

    public ParenStudentRel editHumanRel(ParenStudentRel parenStudentRel){
        parenStudentRel.setUpdateTime(new Date());
        return pareStudentRelDao.save(parenStudentRel);
    }

    public List<ParenStudentRel> findByHumanIDAndDeleteFlag(int humanID,int deleteflag){
        return pareStudentRelDao.findByHumanIDAndDeleteFlag(humanID,deleteflag);
    }

    public List<ParenStudentRel> findByHomeIDAndDeleteFlag(int homeID,int deleteflag){
        return pareStudentRelDao.findByHumanIDAndDeleteFlag(homeID,deleteflag);
    }

}

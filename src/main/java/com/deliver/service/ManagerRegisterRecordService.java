package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.ManagerRegisterRecordDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.ManagerRegisterRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class ManagerRegisterRecordService {
    @Autowired
    private ManagerRegisterRecordDao managerRegisterRecordDao;

    public ManagerRegisterRecord findByRegisterID(int id) {
        return managerRegisterRecordDao.findByRegisterID(id);
    }

    public ManagerRegisterRecord save(ManagerRegisterRecord managerRegisterRecord) {
        managerRegisterRecord.setCreateTime(new Date());
        return managerRegisterRecordDao.save(managerRegisterRecord);
    }

    public int delete(int id) {
        return managerRegisterRecordDao.deleteByRegisterID(id);
    }

    public ManagerRegisterRecord findByRegManagerIDAndCheckFlag(int regManagerID,int checkFlag){
        return managerRegisterRecordDao.findByRegManagerIDAndCheckFlag(regManagerID,checkFlag);
    }

}

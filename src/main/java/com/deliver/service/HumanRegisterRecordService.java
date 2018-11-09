package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.HumanRegisterRecordDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.HumanRegisterRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class HumanRegisterRecordService {
    @Autowired
    private HumanRegisterRecordDao humanRegisterRecordDao;

    public HumanRegisterRecord findByRegisterID(int id) {
        return humanRegisterRecordDao.findByRegisterID(id);
    }

    public HumanRegisterRecord save(HumanRegisterRecord humanRegisterRecord) {
        humanRegisterRecord.setCreateTime(new Date());
        return humanRegisterRecordDao.save(humanRegisterRecord);
    }

    public int delete(int id) {
        return humanRegisterRecordDao.deleteByRegisterID(id);
    }

}

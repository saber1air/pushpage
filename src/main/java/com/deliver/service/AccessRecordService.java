package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.entity.AccessRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class AccessRecordService {
    @Autowired
    private AccessRecordDao accessRecordDao;

    public AccessRecord findByAccessID(int id) {
        return accessRecordDao.findByAccessID(id);
    }

    public AccessRecord save(AccessRecord accessRecord) {
        return accessRecordDao.save(accessRecord);
    }

    public int delete(int id) {
        return accessRecordDao.deleteByAccessID(id);
    }

}

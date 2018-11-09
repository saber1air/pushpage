package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.AtSchoolRecordDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.AtSchoolRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class AtSchoolRecordService {
    @Autowired
    private AtSchoolRecordDao atSchoolRecordDao;

    public AtSchoolRecord findById(int id) {
        return atSchoolRecordDao.findById(id);
    }

    public AtSchoolRecord save(AtSchoolRecord atSchoolRecord) {
        return atSchoolRecordDao.save(atSchoolRecord);
    }

    public int delete(int id) {
        return atSchoolRecordDao.deleteById(id);
    }

}

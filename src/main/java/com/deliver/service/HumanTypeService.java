package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.HumanTypeDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.HumanType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class HumanTypeService {
    @Autowired
    private HumanTypeDao hmanTypeDao;

    public HumanType findById(int id) {
        return hmanTypeDao.findById(id);
    }
    public List<HumanType> findByDeleteFlag(int id) {
        return hmanTypeDao.findByDeleteFlag(id);
    }

    public HumanType save(HumanType HumanType) {
        return hmanTypeDao.save(HumanType);
    }

    public int delete(int id) {
        return hmanTypeDao.deleteById(id);
    }

}

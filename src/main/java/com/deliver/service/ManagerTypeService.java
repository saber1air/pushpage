package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.ManagerTypeDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.ManagerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class ManagerTypeService {
    @Autowired
    private ManagerTypeDao managerTypeDao;

    public ManagerType findById(int id) {
        return managerTypeDao.findById(id);
    }
    public List<ManagerType> findByDeleteFlag(int id) {
        return managerTypeDao.findByDeleteFlag(id);
    }

    public ManagerType save(ManagerType managerType) {
        return managerTypeDao.save(managerType);
    }

    public int delete(int id) {
        return managerTypeDao.deleteById(id);
    }

}

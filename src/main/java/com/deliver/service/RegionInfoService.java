package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.RegionInfoDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.RegionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class RegionInfoService {
    @Autowired
    private RegionInfoDao regionInfoDao;

    public RegionInfo findByRegionID(int id) {
        return regionInfoDao.findByRegionID(id);
    }

    public List<RegionInfo> findByDeleteFlag(int de) {
        return regionInfoDao.findByDeleteFlag(de);
    }

    public RegionInfo save(RegionInfo regionInfo) {
        return regionInfoDao.save(regionInfo);
    }

    public int delete(int id) {
        return regionInfoDao.deleteByRegionID(id);
    }

}

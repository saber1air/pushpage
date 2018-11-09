package com.deliver.service;

import com.deliver.dao.AdvertDao;
import com.deliver.dao.SchoolDao;
import com.deliver.entity.Advert;
import com.deliver.entity.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class AdvertService {
    @Autowired
    private AdvertDao advertDao;

    public List<Advert> findBySchoolIDAndDeleteFlag(int id, int deleteFlag) {
        return advertDao.findBySchoolIDAndDeleteFlag(id,deleteFlag);
    }

    public List<Advert> findBySchoolIDAndUpdateTimeAfter(int id,Date date){
        return advertDao.findBySchoolIDAndUpdateTimeAfter(id, date);
    }


    public Advert save(Advert advert) {
        advert.setCreateTime(new Date());
        advert.setUpdateTime(new Date());
        return advertDao.save(advert);
    }



    public Advert update(Advert advert) {
        advert.setUpdateTime(new Date());
        return advertDao.save(advert);
    }

    public Advert delAdvert(Advert advert) {
        advert.setUpdateTime(new Date());
        advert.setDeleteFlag(1);
        return advertDao.save(advert);
    }

    public int delete(int id) {
        return advertDao.deleteByAdvertID(id);
    }

}

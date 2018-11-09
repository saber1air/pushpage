package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.HumanMediaDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.HumanMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class HumanMediaService {
    @Autowired
    private HumanMediaDao humanMediaDao;

    public HumanMedia findByMediaID(int id) {
        return humanMediaDao.findByMediaIDAndDeleteFlag(id,0);
    }

    public HumanMedia findByHumanIDAndUpdateTimeAfter(int id, Date date) {
        return humanMediaDao.findByHumanIDAndUpdateTimeAfterAndDeleteFlag(id, date, 0);
    }

    public List<HumanMedia> findBySchoolIDAndUpdateTimeAfter(int id, Date date) {
        return humanMediaDao.findBySchoolIDAndUpdateTimeAfterAndCheckFlag(id, date,1);

    }

    public List<HumanMedia> findBySchoolID(int id) {
        return humanMediaDao.findBySchoolIDAndCheckFlag(id,1);
    }



    public HumanMedia save(HumanMedia humanMedia) {
        return humanMediaDao.save(humanMedia);
    }

    public int delete(int id) {
        return humanMediaDao.deleteByMediaID(id);
    }
    public HumanMedia addMedia(HumanMedia humanMedia){
        humanMedia.setCreateTime(new Date());
        humanMedia.setUpdateTime(new Date());
        humanMedia.setDeleteFlag(0);
        return humanMediaDao.save(humanMedia);
    }

    public HumanMedia editMedia(HumanMedia humanMedia){
        humanMedia.setUpdateTime(new Date());
        humanMedia.setDeleteFlag(0);
        return humanMediaDao.save(humanMedia);
    }

    public HumanMedia delMedia(HumanMedia humanMedia){
        humanMedia.setUpdateTime(new Date());
        humanMedia.setDeleteFlag(1);
        return humanMediaDao.save(humanMedia);
    }

    public List<HumanMedia> findByHumanIDAndDeleteFlag(int id ,int deleteFlag){
        return humanMediaDao.findByHumanIDAndDeleteFlagOrderByUpdateTimeDesc(id,deleteFlag);
    }

    public List<HumanMedia> findByHumanIDAndDeleteFlagAndCheckFlag(int id ,int deleteFlag,int checkFlag){
        return humanMediaDao.findByHumanIDAndDeleteFlagAndCheckFlag(id,deleteFlag,checkFlag);
    }

}

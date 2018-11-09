package com.deliver.dao;

import com.deliver.entity.HumanInfo;
import com.deliver.entity.HumanMedia;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */
@CacheConfig(cacheNames = "humanMedia")
public interface HumanMediaDao extends JpaRepository<HumanMedia, Integer> {
    //@Cacheable()  //查询缓存
    HumanMedia findByMediaIDAndDeleteFlag(int id,int deleteFlag);


    HumanMedia findByHumanIDAndUpdateTimeAfterAndDeleteFlag(int id, Date date, int deleteFlag);

    List<HumanMedia> findBySchoolIDAndUpdateTimeAfterAndCheckFlag(int id, Date date,int checkFlag);

    List<HumanMedia> findBySchoolID(int id);
    List<HumanMedia> findBySchoolIDAndCheckFlag(int id ,int checkFlag);

    List<HumanMedia> findByHumanIDAndDeleteFlag(int id ,int deleteFlag);

    List<HumanMedia> findByHumanIDAndDeleteFlagOrderByUpdateTimeDesc(int id ,int deleteFlag);



    List<HumanMedia> findByHumanIDAndDeleteFlagAndCheckFlag(int id ,int deleteFlag,int checkFlag);

    /**
     * 新增或修改时
     */
    @CachePut()//往缓存中新增
    @Override
    HumanMedia save(HumanMedia humanMedia);

    @Transactional   //事务管理
    @Modifying
    int deleteByMediaID(int id);

}


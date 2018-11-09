package com.deliver.dao;

import com.deliver.entity.MacInfo;
import com.deliver.entity.ManagerRegisterRecord;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pdl on 2018/9/13.
 */
@CacheConfig(cacheNames = "managerRegisterRecord")
public interface ManagerRegisterRecordDao extends JpaRepository<ManagerRegisterRecord, Integer> {
    @Cacheable()  //查询缓存
    ManagerRegisterRecord findByRegisterID(int id);

    /**
     * 新增或修改时
     */
    @CachePut()//往缓存中新增
    @Override
    ManagerRegisterRecord save(ManagerRegisterRecord managerRegisterRecord);

    @Transactional   //事务管理
    @Modifying
    int deleteByRegisterID(int id);

    ManagerRegisterRecord findByRegManagerIDAndCheckFlag(int regManagerID,int checkFlag);

}


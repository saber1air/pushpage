package com.deliver.dao;

import com.deliver.entity.ManagerRegisterRecord;
import com.deliver.entity.ManagerType;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */
@CacheConfig(cacheNames = "managerType")
public interface ManagerTypeDao extends JpaRepository<ManagerType, Integer> {
    @Cacheable()  //查询缓存
    ManagerType findById(int id);

    List<ManagerType> findByDeleteFlag(int id);

    /**
     * 新增或修改时
     */
    @CachePut()//往缓存中新增
    @Override
    ManagerType save(ManagerType managerType);

    @Transactional   //事务管理
    @Modifying
    int deleteById(int id);

}


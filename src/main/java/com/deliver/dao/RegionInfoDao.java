package com.deliver.dao;

import com.deliver.entity.RegionInfo;
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
@CacheConfig(cacheNames = "regionInfo")
public interface RegionInfoDao extends JpaRepository<RegionInfo, Integer> {
    @Cacheable()  //查询缓存
    RegionInfo findByRegionID(int id);


    List<RegionInfo> findByDeleteFlag(int delete);

    /**
     * 新增或修改时
     */
    @CachePut()//往缓存中新增
    @Override
    RegionInfo save(RegionInfo regionInfo);

    @Transactional   //事务管理
    @Modifying
    int deleteByRegionID(int id);

}


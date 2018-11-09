package com.deliver.dao;

import com.deliver.entity.AccessRecord;
import com.deliver.entity.VersionInfo;
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
@CacheConfig(cacheNames = "accessRecord")
public interface VersionInfoDao extends JpaRepository<VersionInfo, Integer> {
    //@Cacheable()  //查询缓存
    VersionInfo findByVersionID(int id);

    /**
     * 新增或修改时
     */
    @CachePut()//往缓存中新增
    @Override
    VersionInfo save(VersionInfo versionInfo);

    @Transactional   //事务管理
    @Modifying
    int deleteByVersionID(int id);

    List<VersionInfo> findByVersionNumAndAppID(String versionNum, String appID);

    List<VersionInfo> findByUpdateFlagAndDeleteFlag(int updateFlag, int deleteFlag);

}


package com.deliver.dao;

import com.deliver.entity.Advert;
import com.deliver.entity.School;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */
@CacheConfig(cacheNames = "advert")
public interface AdvertDao extends JpaRepository<Advert, Integer> {
    //@Cacheable()  //查询缓存
    List<Advert> findBySchoolIDAndDeleteFlag(int id, int deleteFlag);

    Advert findByAdvertIDAndDeleteFlag(int id,int deleteFlag);

    List<Advert> findByDeleteFlag(int deleteFlag);

    List<Advert> findBySchoolIDAndUpdateTimeAfter(int id, Date date);

    /**
     * 新增或修改时
     */
    @CachePut()//往缓存中新增
    @Override
    Advert save(Advert advert);

    @Transactional   //事务管理
    @Modifying
    int deleteByAdvertID(int id);

}


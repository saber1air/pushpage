package com.deliver.dao;

import com.deliver.entity.AccessRecord;
import com.deliver.entity.DeliverRecord;
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
@CacheConfig(cacheNames = "deliverRecord")
public interface DeliverRecordDao extends JpaRepository<DeliverRecord, Integer> {
    //@Cacheable()  //查询缓存
    DeliverRecord findByDeliverID(int deliverID);

    /**
     * 新增或修改时
     */
    //@CachePut()//往缓存中新增
    @Override
    DeliverRecord save(DeliverRecord deliverRecord);

    @Transactional   //事务管理
    @Modifying
    int deleteByDeliverID(int id);


}


package com.deliver.dao;

import com.deliver.entity.ManagerType;
import com.deliver.entity.ParenStudentRel;
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
@CacheConfig(cacheNames = "parenStudentRel")
public interface PareStudentRelDao extends JpaRepository<ParenStudentRel, Integer> {
    //@Cacheable()  //查询缓存
    ParenStudentRel findByRelid(int id);


    List<ParenStudentRel> findByHumanIDAndUpdateTimeAfterAndDeleteFlag(int id, Date date, int deleteflag);

    List<ParenStudentRel> findBySchoolIDAndUpdateTimeAfterAndCheckFlag(int id, Date date,int checkFlag);

    List<ParenStudentRel> findBySchoolIDAndDeleteFlag(int id, int deleteflag);
    List<ParenStudentRel> findBySchoolIDAndCheckFlag(int id,int checkFlag);

    List<ParenStudentRel> findByHumanIDAndCheckFlagAndDeleteFlag(int id,int checkFlag,int deleteflag);

    List<ParenStudentRel> findByHumanIDAndDeleteFlag(int humanID,int deleteflag);

    List<ParenStudentRel> findByHomeIDAndDeleteFlag(int homeID,int deleteflag);

    /**
     * 新增或修改时
     */
    @CachePut()//往缓存中新增
    @Override
    ParenStudentRel save(ParenStudentRel parenStudentRel);

    @Transactional   //事务管理
    @Modifying
    int deleteByRelid(int id);

    //@Cacheable()  //查询缓存
    List<ParenStudentRel> findByHomeIDAndHumanID(int homeID, int humanID);

    List<ParenStudentRel> findByHomeIDAndHumanIDAndSchoolID(int homeID, int humanID,int schoolid);

}


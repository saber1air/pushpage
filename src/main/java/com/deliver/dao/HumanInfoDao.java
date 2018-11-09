package com.deliver.dao;

import com.deliver.entity.GradeInfo;
import com.deliver.entity.HumanInfo;
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
@CacheConfig(cacheNames = "humanInfo")
public interface HumanInfoDao extends JpaRepository<HumanInfo, Integer> {
    //@Cacheable()  //查询缓存
    //HumanInfo findByHumanID(int id);

    HumanInfo findByHumanIDAndDeleteFlag(int id,int deleteFlag);


    List<HumanInfo> findBySchoolIDAndUpdateTimeAfterAndCheckFlag(int id, Date date, int checkFlag);

    List<HumanInfo> findBySchoolIDAndDeleteFlagAndCheckFlag(int id, int deleteFlag,int checkFlag);
    List<HumanInfo> findBySchoolIDAndCheckFlag(int id, int checkFlag);


    //@Cacheable()  //查询缓存
    List<HumanInfo> findByHumanNameAndDeleteFlag(String humanName,int deleteFlag);

    //@Cacheable()  //查询缓存
    List<HumanInfo> findByTelAndDeleteFlag(String tel,int deleteFlag);

    //@Cacheable()  //查询缓存
    HumanInfo findByHumanNameAndTelAndDeleteFlag(String humanName,String tel,int deleteFlag);

    //HumanInfo findByHumanNameAndTelAndDeleteFlag(String humanName,String tel,int deleteFlag);

    HumanInfo findByHumanNameAndSchoolIDAndGradeIDAndClassIDAndDeleteFlag(String humanName,int schoolID,int gradeID,int classID,int deleteFlag);
    List<HumanInfo> findByTelAndPasswordAndDeleteFlag(String tel,String password,int deleteFlag);

    List<HumanInfo> findByClassIDAndCheckFlagAndHumanTypeIn(int classID,int checkFlag,String humanType);

    List<HumanInfo> findBySchoolIDAndCheckFlagAndHumanType(int schoolID,int checkFlag,int humanType);

    List<HumanInfo> findByCheckFlagAndHumanTypeAndDeleteFlag(int checkFlag,int humanType,int deleteFlag);

    List<HumanInfo> findByCheckFlagAndHumanType(int checkFlag,int humanType);

    /**
     * 新增或修改时
     */
    @CachePut()//往缓存中新增
    @Override
    HumanInfo save(HumanInfo humanInfo);

    @Transactional   //事务管理
    @Modifying
    int deleteByHumanID(int id);

}


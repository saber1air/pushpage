package com.deliver.dao;

import com.deliver.Post;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pdl on 2018/9/13.
 */
@CacheConfig(cacheNames = "post")
public interface PostDao extends JpaRepository<Post, Integer> {
    @Cacheable()  //查询缓存
    Post findById(int id);

    /**
     * 新增或修改时
     */
    @CachePut()//往缓存中新增
    @Override
    Post save(Post post);

    @Transactional   //事务管理
    @Modifying
    int deleteById(int id);


}


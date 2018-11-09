package com.deliver.util;

import org.omg.CORBA.Any;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by pdl on 2018/9/18.
 */
public class RedisFlatSessionStore implements FlatSessionStore{
    @Resource
    protected RedisTemplate<Serializable, Serializable> redisTemplate = new RedisTemplate();


    public Boolean removeAttribute(String key){
        redisTemplate.delete("$sessionPrefix::$key");
        return true;
    }

    public String getAttribute(String key){
        BoundValueOperations ops = redisTemplate.boundValueOps("$sessionPrefix::$key");
        return ops.get().toString();
    }

    public void setAttribute(String key, Object value) {
        redisTemplate.boundValueOps(key).set(value.toString());
    }

    public void setAttribute(String key, Object value,Long ttl) {
        BoundValueOperations ops = redisTemplate.boundValueOps("$sessionPrefix::$key");
        ops.set(value, ttl, TimeUnit.SECONDS);
    }
}

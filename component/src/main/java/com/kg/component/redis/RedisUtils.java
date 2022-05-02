package com.kg.component.redis;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author ziro
 * @date 2020/5/19 19:30
 */
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 读取缓存
     */
    public Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存（永不过期）
     * 删除须手动删除
     */
    public boolean setNoTimeLimit(final String key, Object value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存（默认10分钟过期）
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, 10, TimeUnit.MINUTES);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存（超时时间，单位s）
     *
     * @param timeout 超时时间，单位s
     */
    public boolean set(final String key, Object value, Long timeout) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存（加到期时间）
     *
     * @param endtime 到期时间
     */
    public boolean set(final String key, Object value, Date endtime) {
        boolean result = false;
        try {
            Long timeout = DateUtil.between(DateUtil.date(), endtime, DateUnit.SECOND);
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新缓存超期时间
     *
     * @param timeout 超时时间，单位s
     */
    public boolean setExpire(final String key, Long timeout) {
        boolean result = false;
        try {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除缓存
     */
    public boolean delete(final String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断key是否存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

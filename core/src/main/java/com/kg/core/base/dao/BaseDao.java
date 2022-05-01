package com.kg.core.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.core.base.model.BaseEntity;

/**
 * @author ziro
 * @date 2022/4/27 22:35
 */
public interface BaseDao<T extends BaseEntity> extends BaseMapper<T> {
}

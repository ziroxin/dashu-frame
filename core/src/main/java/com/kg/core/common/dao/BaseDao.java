package com.kg.core.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.core.common.model.BaseEntity;

/**
 * @author ziro
 * @date 2022/4/27 22:35
 */
public interface BaseDao<T extends BaseEntity> extends BaseMapper<T> {
}

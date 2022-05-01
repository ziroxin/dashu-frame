package com.kg.core.base.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.core.base.mapper.BaseDao;
import com.kg.core.base.model.BaseEntity;

/**
 * @author ziro
 * @date 2022/4/27 22:37
 */
public class BaseServiceImpl<M extends BaseDao<T>, T extends BaseEntity>
        extends ServiceImpl<M, T> implements BaseService<T> {

}

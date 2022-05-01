package com.kg.core.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kg.core.base.model.BaseEntity;

/**
 * @author ziro
 * @date 2022/4/27 22:37
 */
public interface BaseService<T extends BaseEntity> extends IService<T> {
}

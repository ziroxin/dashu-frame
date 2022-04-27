package com.kg.core.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kg.core.common.model.BaseEntity;

/**
 * @author ziro
 * @date 2022/4/27 22:37
 */
public interface BaseService<T extends BaseEntity> extends IService<T> {
}

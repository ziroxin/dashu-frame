package com.kg.module.cangroup.mapper;

import com.kg.module.cangroup.entity.CanGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.module.canshop.entity.CanUserShop;

/**
 * <p>
 * 菜品分组表 Mapper 接口
 * </p>
 *
 * @author dazui
 * @since 2022-06-24
 */
public interface CanGroupMapper extends BaseMapper<CanGroup> {

    CanUserShop getUserShop(String userId);
}

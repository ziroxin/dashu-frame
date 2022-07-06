package com.kg.module.canshop.mapper;

import com.kg.module.canshop.entity.CanUserShop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户店铺中间表 Mapper 接口
 * </p>
 *
 * @author dazui
 * @since 2022-06-27
 */
public interface CanUserShopMapper extends BaseMapper<CanUserShop> {

    Integer getUserById(String userId);

}

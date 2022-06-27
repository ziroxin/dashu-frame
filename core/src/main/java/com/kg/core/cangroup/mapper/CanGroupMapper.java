package com.kg.core.cangroup.mapper;

import com.kg.core.cangroup.entity.CanGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.core.canshop.dto.CanUserShopDTO;
import com.kg.core.canshop.entity.CanUserShop;

import java.util.List;

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

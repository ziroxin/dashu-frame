package com.kg.core.cangroup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kg.core.cangroup.entity.CanGroup;
import com.kg.core.canshop.dto.CanUserShopDTO;
import com.kg.core.canshop.entity.CanUserShop;

import java.util.List;

/**
 * <p>
 * 菜品分组表 服务类
 * </p>
 *
 * @author dazui
 * @since 2022-06-24
 */
public interface ICanGroupService extends IService<CanGroup> {

    CanUserShop getUserShop(String userId);
}

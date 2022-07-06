package com.kg.module.cangroup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kg.module.cangroup.entity.CanGroup;
import com.kg.module.canshop.entity.CanUserShop;

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

    List<CanGroup> getGroupById(String shopId);
}

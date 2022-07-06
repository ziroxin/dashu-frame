package com.kg.module.cangroup.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.module.cangroup.entity.CanGroup;
import com.kg.module.cangroup.mapper.CanGroupMapper;
import com.kg.module.cangroup.service.ICanGroupService;
import com.kg.module.canshop.entity.CanUserShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜品分组表 服务实现类
 * </p>
 *
 * @author dazui
 * @since 2022-06-24
 */
@Service
public class CanGroupServiceImpl extends ServiceImpl<CanGroupMapper, CanGroup> implements ICanGroupService {

    @Autowired
    private CanGroupMapper canGroupMapper;

    @Override
    public CanUserShop getUserShop(String userId) {
        return canGroupMapper.getUserShop(userId);
    }

    @Override
    public List<CanGroup> getGroupById(String shopId) {
        return canGroupMapper.getGroupById(shopId);
    }
}

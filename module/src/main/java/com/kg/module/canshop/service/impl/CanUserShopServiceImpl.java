package com.kg.module.canshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.module.canshop.entity.CanUserShop;
import com.kg.module.canshop.mapper.CanUserShopMapper;
import com.kg.module.canshop.service.ICanUserShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户店铺中间表 服务实现类
 * </p>
 *
 * @author dazui
 * @since 2022-06-27
 */
@Service
public class CanUserShopServiceImpl extends ServiceImpl<CanUserShopMapper, CanUserShop> implements ICanUserShopService {

    @Autowired
    private CanUserShopMapper canUserShopMapper;


    @Override
    public Integer getUserById(String userId) {
        return canUserShopMapper.getUserById(userId);
    }

}

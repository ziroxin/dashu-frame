package com.kg.module.canorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.module.canorder.entity.CanOrder;
import com.kg.module.canorder.mapper.CanOrderMapper;
import com.kg.module.canorder.service.ICanOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author dazui
 * @since 2022-06-27
 */
@Service
public class CanOrderServiceImpl extends ServiceImpl<CanOrderMapper, CanOrder> implements ICanOrderService {

    @Autowired
    private CanOrderMapper canOrderMapper;

    @Override
    public List<CanOrder> shopListByTime(String startTime, String endTime) {
        return canOrderMapper.shopListByTime(startTime, endTime);
    }

}

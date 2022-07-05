package com.kg.module.canorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.module.canorder.entity.CanOrderDetails;
import com.kg.module.canorder.mapper.CanOrderDetailsMapper;
import com.kg.module.canorder.service.ICanOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author dazui
 * @since 2022-06-28
 */
@Service
public class CanOrderDetailsServiceImpl extends ServiceImpl<CanOrderDetailsMapper, CanOrderDetails> implements ICanOrderDetailsService {

    @Autowired
    private CanOrderDetailsMapper canOrderDetailsMapper;

    @Override
    public List<CanOrderDetails> getOrderDetailsById(String orderId) {
        return canOrderDetailsMapper.getOrderDetailsById(orderId);
    }

}

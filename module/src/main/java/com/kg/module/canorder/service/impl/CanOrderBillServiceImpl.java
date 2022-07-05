package com.kg.module.canorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.module.canorder.entity.CanOrderBill;
import com.kg.module.canorder.mapper.CanOrderBillMapper;
import com.kg.module.canorder.service.ICanOrderBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发票表 服务实现类
 * </p>
 *
 * @author dazui
 * @since 2022-06-28
 */
@Service
public class CanOrderBillServiceImpl extends ServiceImpl<CanOrderBillMapper, CanOrderBill> implements ICanOrderBillService {

    @Autowired
    private CanOrderBillMapper canOrderBillMapper;

    @Override
    public CanOrderBill getOrderBillById(String orderId) {
        return canOrderBillMapper.getOrderBillById(orderId);
    }
}

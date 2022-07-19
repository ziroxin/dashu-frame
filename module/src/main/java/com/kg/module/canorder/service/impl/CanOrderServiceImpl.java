package com.kg.module.canorder.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.component.utils.GuidUtils;
import com.kg.module.canapi.dto.CanOrderDetailListDTO;
import com.kg.module.canorder.dto.CanOrderDTO;
import com.kg.module.canorder.entity.CanOrder;
import com.kg.module.canorder.entity.CanOrderDetails;
import com.kg.module.canorder.mapper.CanOrderMapper;
import com.kg.module.canorder.service.ICanOrderDetailsService;
import com.kg.module.canorder.service.ICanOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    @Autowired
    private ICanOrderDetailsService orderDetailsService;

    @Override
    public List<CanOrder> shopListByTime(String startTime, String endTime) {
        return canOrderMapper.shopListByTime(startTime, endTime);
    }

    @Override
    public List<CanOrderDetailListDTO> getOrderDetailList(String orderId) {
        return canOrderMapper.getOrderDetailList(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveOrder(CanOrderDTO canOrderDTO) {
        canOrderDTO.setOrderId(GuidUtils.getUuid());
        canOrderDTO.setDiningTime(LocalDateTime.now());
        // 订单是否完成（0是，1否）
        canOrderDTO.setOrderComplete("0");
        canOrderDTO.setCreateTime(LocalDateTime.now());
        CanOrder canOrder = JSONUtil.toBean(JSONUtil.parseObj(canOrderDTO), CanOrder.class);
        save(canOrder);

        // 保存订单详情
        List<CanOrderDetails> canOrderDetails = canOrderDTO.getCanOrderDetails();
        for (CanOrderDetails order : canOrderDetails) {
            order.setOrderDetailsId(GuidUtils.getUuid());
            order.setOrderId(canOrderDTO.getOrderId());
            order.setCreateTime(LocalDateTime.now());
        }
        orderDetailsService.saveBatch(canOrderDetails);
        String orderId = canOrderDTO.getOrderId();
        return orderId;
    }

}

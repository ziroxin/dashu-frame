package com.kg.module.canorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kg.module.canapi.dto.CanOrderDetailListDTO;
import com.kg.module.canorder.dto.CanOrderDTO;
import com.kg.module.canorder.entity.CanOrder;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author dazui
 * @since 2022-06-27
 */
public interface ICanOrderService extends IService<CanOrder> {

    List<CanOrder> shopListByTime(String startTime, String endTime);

    List<CanOrderDetailListDTO> getOrderDetailList(String orderId);

    /**
     * 保存订单信息
     *
     * @param canOrderDTO 订单数据
     */
    String saveOrder(CanOrderDTO canOrderDTO);
}

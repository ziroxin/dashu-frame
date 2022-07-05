package com.kg.module.canorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kg.module.canorder.entity.CanOrderDetails;

import java.util.List;

/**
 * <p>
 * 订单详情表 服务类
 * </p>
 *
 * @author dazui
 * @since 2022-06-28
 */
public interface ICanOrderDetailsService extends IService<CanOrderDetails> {

    List<CanOrderDetails> getOrderDetailsById(String orderId);

}

package com.kg.module.canorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kg.module.canorder.entity.CanOrderBill;

/**
 * <p>
 * 发票表 服务类
 * </p>
 *
 * @author dazui
 * @since 2022-06-28
 */
public interface ICanOrderBillService extends IService<CanOrderBill> {

    CanOrderBill getOrderBillById(String orderId);
}

package com.kg.module.canorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.module.canorder.entity.CanOrderBill;

import java.util.List;

/**
 * <p>
 * 发票表 Mapper 接口
 * </p>
 *
 * @author dazui
 * @since 2022-06-28
 */
public interface CanOrderBillMapper extends BaseMapper<CanOrderBill> {

    CanOrderBill getOrderBillById(String orderId);

    boolean deleteBill(List<String> orderIds);

}

package com.kg.module.canorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.module.canorder.entity.CanOrderDetails;

import java.util.List;

/**
 * <p>
 * 订单详情表 Mapper 接口
 * </p>
 *
 * @author dazui
 * @since 2022-06-28
 */
public interface CanOrderDetailsMapper extends BaseMapper<CanOrderDetails> {

    List<CanOrderDetails> getOrderDetailsById(String orderId);

    boolean deleteDetails(List<String> orderIds);
}

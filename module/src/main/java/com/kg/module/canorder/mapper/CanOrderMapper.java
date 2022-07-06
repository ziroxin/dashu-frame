package com.kg.module.canorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.module.canorder.entity.CanOrder;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author dazui
 * @since 2022-06-27
 */
public interface CanOrderMapper extends BaseMapper<CanOrder> {

    List<CanOrder> shopListByTime(String startTime, String endTime);
}

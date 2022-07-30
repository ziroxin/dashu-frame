package com.kg.module.cantable.mapper;

import com.kg.module.cantable.dto.CanTableDTO;
import com.kg.module.cantable.entity.CanTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 餐桌表 Mapper 接口
 * </p>
 *
 * @author dazui
 * @since 2022-06-27
 */
public interface CanTableMapper extends BaseMapper<CanTable> {

    List<CanTableDTO> getTableList();
}

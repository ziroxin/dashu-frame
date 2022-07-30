package com.kg.module.cantable.service;

import com.kg.module.cantable.dto.CanTableDTO;
import com.kg.module.cantable.entity.CanTable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 餐桌表 服务类
 * </p>
 *
 * @author dazui
 * @since 2022-06-27
 */
public interface ICanTableService extends IService<CanTable> {
    List<CanTableDTO> getTableList();
}

package com.kg.module.cantable.service.impl;

import com.kg.module.canqrcode.entity.CanTableQrcode;
import com.kg.module.cantable.dto.CanTableDTO;
import com.kg.module.cantable.entity.CanTable;
import com.kg.module.cantable.mapper.CanTableMapper;
import com.kg.module.cantable.service.ICanTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 餐桌表 服务实现类
 * </p>
 *
 * @author dazui
 * @since 2022-06-27
 */
@Service
public class CanTableServiceImpl extends ServiceImpl<CanTableMapper, CanTable> implements ICanTableService {

    @Autowired
    private CanTableMapper canTableMapper;

    @Override
    public List<CanTableDTO> getTableList() {
        return canTableMapper.getTableList();
    }
}

package com.kg.core.zapi.service.impl;

import com.kg.core.zapi.entity.ZApi;
import com.kg.core.zapi.mapper.ZApiMapper;
import com.kg.core.zapi.service.IZApiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * API信息表 服务实现类
 * </p>
 *
 * @author ziro
 * @since 2022-05-05
 */
@Service
public class ZApiServiceImpl extends ServiceImpl<ZApiMapper, ZApi> implements IZApiService {

    @Autowired
    private ZApiMapper zApiMapper;

    @Override
    public List<String> listApiByUserId(String userId) {
        return zApiMapper.listApiByUserId(userId);
    }
}

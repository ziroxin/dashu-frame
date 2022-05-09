package com.kg.core.zpermission.service.impl;

import com.kg.core.zpermission.entity.ZPermission;
import com.kg.core.zpermission.mapper.ZPermissionMapper;
import com.kg.core.zpermission.service.IZPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资源权限表 服务实现类
 * </p>
 *
 * @author ziro
 * @since 2022-05-09
 */
@Service
public class ZPermissionServiceImpl extends ServiceImpl<ZPermissionMapper, ZPermission> implements IZPermissionService {

    @Autowired
    private ZPermissionMapper permissionMapper;

    @Override
    public List<ZPermission> listPermissionByUserId(String userId) {
        return permissionMapper.listPermissionByUserId(userId);
    }
}

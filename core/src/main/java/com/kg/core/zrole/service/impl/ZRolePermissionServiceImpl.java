package com.kg.core.zrole.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kg.core.zrole.dto.ZRolePermissionSaveDTO;
import com.kg.core.zrole.entity.ZRolePermission;
import com.kg.core.zrole.mapper.ZRolePermissionMapper;
import com.kg.core.zrole.service.IZRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author ziro
 * @since 2022-05-21
 */
@Service
public class ZRolePermissionServiceImpl extends ServiceImpl<ZRolePermissionMapper, ZRolePermission> implements IZRolePermissionService {

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveRolePermission(ZRolePermissionSaveDTO rolePermissionSaveDTO) {
        List<ZRolePermission> collect = Arrays.stream(rolePermissionSaveDTO.getPermissionIds())
                .map(permissionId -> {
                    ZRolePermission rolePermission = new ZRolePermission();
                    rolePermission.setRoleId(rolePermissionSaveDTO.getRoleId());
                    rolePermission.setPermissionId(permissionId);
                    return rolePermission;
                }).collect(Collectors.toList());
        // 先删除
        QueryWrapper<ZRolePermission> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ZRolePermission::getRoleId, rolePermissionSaveDTO.getRoleId());
        remove(wrapper);
        // 再保存
        saveBatch(collect);
    }
}

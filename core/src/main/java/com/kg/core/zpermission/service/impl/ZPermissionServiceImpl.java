package com.kg.core.zpermission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.core.zpermission.dto.ZPermissionDTO;
import com.kg.core.zpermission.dto.convert.ZPermissionConvert;
import com.kg.core.zpermission.entity.ZPermission;
import com.kg.core.zpermission.enums.PermissionTypeEnum;
import com.kg.core.zpermission.mapper.ZPermissionMapper;
import com.kg.core.zpermission.service.IZPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private ZPermissionConvert permissionConvert;

    @Value("${com.kg.developer.user.ids}")
    private String DeveloperUserIds;

    // 查询该用户有权限的所有资源
    @Override
    public Map<String, Object> listPermissionByUserId(String userId) {
        Map<String, Object> map = new HashMap<>();
        // 1 查出该用户所有资源
        List<ZPermission> zPermissions;
        if ((DeveloperUserIds + ",").contains(userId + ",")) {
            // 开发管理员，拥有全部资源权限
            QueryWrapper<ZPermission> wrapper = new QueryWrapper<>();
            wrapper.lambda().orderByAsc(ZPermission::getPermissionOrder);
            zPermissions = list(wrapper);
        } else {
            // 非开发管理员，根据userId查询资源权限
            zPermissions = permissionMapper.listPermissionByUserId(userId);
        }
        // 2 所有权限列表
        map.put("permissions", zPermissions);
        // 3 组装路由
        map.put("perrouters", getChildrenPermission(zPermissions, "-1"));
        return map;
    }

    // 迭代查询子菜单
    private List<ZPermissionDTO> getChildrenPermission(List<ZPermission> zPermissions, String parentId) {
        return zPermissions.stream()
                .filter(zPermission -> zPermission.getParentId().equals(parentId))
                .map(perm -> {
                    ZPermissionDTO zPermissionDTO = permissionConvert.entityToDto(perm);
                    String type = zPermissionDTO.getPermissionType();
                    // 只有路由和外链，作为菜单返回
                    if (StringUtils.hasText(type)) {
                        if (Integer.parseInt(type) == PermissionTypeEnum.ROUTER.getCode() ||
                                Integer.parseInt(type) == PermissionTypeEnum.LINK.getCode()) {
                            // 迭代查询子菜单
                            List<ZPermissionDTO> childernList = getChildrenPermission(zPermissions, zPermissionDTO.getPermissionId());
                            if (childernList != null && childernList.size() > 0) {
                                zPermissionDTO.setChildren(childernList);
                            }
                            return zPermissionDTO;
                        }
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }
}

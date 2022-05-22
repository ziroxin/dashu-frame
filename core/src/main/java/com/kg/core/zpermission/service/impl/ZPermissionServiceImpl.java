package com.kg.core.zpermission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.core.zpermission.dto.ZPermissionDTO;
import com.kg.core.zpermission.dto.ZRolePermissionDTO;
import com.kg.core.zpermission.dto.convert.ZPermissionConvert;
import com.kg.core.zpermission.dto.convert.ZRolePermissionConvert;
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
    @Autowired
    private ZRolePermissionConvert rolePermissionConvert;

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
                .filter(obj -> obj != null)
                .collect(Collectors.toList());
    }

    /**
     * 查询资源树表格
     */
    @Override
    public List<ZPermissionDTO> treeList() {
        QueryWrapper<ZPermission> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByAsc(ZPermission::getPermissionOrder);
        List<ZPermission> list = list(wrapper);
        return treeListChildren(list, "-1");
    }

    // 迭代查询子菜单
    private List<ZPermissionDTO> treeListChildren(List<ZPermission> zPermissions, String parentId) {
        return zPermissions.stream()
                .filter(zPermission -> zPermission.getParentId().equals(parentId))
                .map(perm -> {
                    ZPermissionDTO zPermissionDTO = permissionConvert.entityToDto(perm);
                    // 迭代查询子菜单
                    List<ZPermissionDTO> childrenList = treeListChildren(zPermissions, zPermissionDTO.getPermissionId());
                    if (childrenList != null && childrenList.size() > 0) {
                        zPermissionDTO.setChildren(childrenList);
                    }
                    return zPermissionDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ZRolePermissionDTO> listForRole() {
        // 全部列表
        QueryWrapper<ZPermission> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByAsc(ZPermission::getPermissionOrder)
                .eq(ZPermission::getPermissionType, PermissionTypeEnum.ROUTER.getCode())
                .or().eq(ZPermission::getPermissionType, PermissionTypeEnum.LINK.getCode());
        List<ZPermission> list = list(wrapper);
        // 路由列表
        QueryWrapper<ZPermission> wrapper2 = new QueryWrapper<>();
        wrapper2.lambda().orderByAsc(ZPermission::getPermissionOrder)
                .eq(ZPermission::getPermissionType, PermissionTypeEnum.BUTTON.getCode())
                .or().eq(ZPermission::getPermissionType, PermissionTypeEnum.OTHER.getCode());
        List<ZPermission> list2 = list(wrapper2);
        return rolePermissionChildren(list, list2, "-1");
    }

    private List<ZRolePermissionDTO> rolePermissionChildren(List<ZPermission> zPermissions,
                                                            List<ZPermission> buttonList, String parentId) {
        return zPermissions.stream()
                .filter(zPermission -> zPermission.getParentId().equals(parentId))
                .map(perm -> {
                    ZRolePermissionDTO zPermissionDTO = rolePermissionConvert.entityToDto(perm);
                    // 查询按钮等列表
                    zPermissionDTO.setButtonList(buttonList.stream()
                            .filter(btn -> btn.getParentId().equals(zPermissionDTO.getPermissionId()))
                            .collect(Collectors.toList()));
                    // 迭代查询子菜单
                    List<ZRolePermissionDTO> childrenList = rolePermissionChildren(zPermissions,
                            buttonList, zPermissionDTO.getPermissionId());
                    if (childrenList != null && childrenList.size() > 0) {
                        zPermissionDTO.setChildren(childrenList);
                    }
                    return zPermissionDTO;
                })
                .collect(Collectors.toList());
    }
}

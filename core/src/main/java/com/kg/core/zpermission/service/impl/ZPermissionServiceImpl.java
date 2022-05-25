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
import com.kg.core.zrole.entity.ZRolePermission;
import com.kg.core.zrole.service.IZRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
    private IZRolePermissionService rolePermissionService;
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
                .filter(zPermission -> StringUtils.hasText(zPermission.getParentId()) && zPermission.getParentId().equals(parentId))
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

    /**
     * 查询资源树表格二号
     */
    @Override
    public List<ZPermissionDTO> permissionTreeList() {
        QueryWrapper<ZPermission> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByAsc(ZPermission::getPermissionOrder);
        List<ZPermission> list = list(wrapper);
        List<String> list2 = new ArrayList<>();
        list2.add("0");
        list2.add("2");
        return permissionTreeListChildren(list, list2, "-1");
    }

    // 迭代查询子菜单二号
    private List<ZPermissionDTO> permissionTreeListChildren(List<ZPermission> zPermissions, List<String> list2, String parentId) {
        return zPermissions.stream()
                .filter(zPermission -> StringUtils.hasText(zPermission.getParentId()) && zPermission.getParentId().equals(parentId))
                .filter(zPermission -> list2.contains(zPermission.getPermissionType()))
                .map(perm -> {
                    ZPermissionDTO zPermissionDTO = permissionConvert.entityToDto(perm);
                    // 迭代查询子菜单
                    List<ZPermissionDTO> childernList = permissionTreeListChildren(zPermissions, list2, zPermissionDTO.getPermissionId());
                    if (childernList != null && childernList.size() > 0) {
                        zPermissionDTO.setChildren(childernList);
                    }
                    return zPermissionDTO;
                })
                .collect(Collectors.toList());
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

    /**
     * 角色权限列表树
     */
    @Override
    public List<ZRolePermissionDTO> listForRole(String roleId) {
        // 角色拥有的权限列表
        List<String> rolePermissionList = null;
        if (StringUtils.hasText(roleId)) {
            QueryWrapper<ZRolePermission> roleWrapper = new QueryWrapper<>();
            roleWrapper.lambda().eq(ZRolePermission::getRoleId, roleId);
            rolePermissionList = rolePermissionService.list(roleWrapper)
                    .stream().map(rp -> rp.getPermissionId()).collect(Collectors.toList());
        }
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
        return rolePermissionChildren(list, list2, rolePermissionList, "-1");
    }

    private List<ZRolePermissionDTO> rolePermissionChildren(
            List<ZPermission> zPermissions, List<ZPermission> buttonList,
            List<String> rolePermissionList, String parentId) {
        //.stream,将数据转化为流
        return zPermissions.stream()
                //.filter,过滤出元素
                .filter(zPermission -> zPermission.getParentId().equals(parentId))
                .map(perm -> {
                    ZRolePermissionDTO zPermissionDTO = rolePermissionConvert.entityToDto(perm);
                    // 查询按钮等列表
                    zPermissionDTO.setButtonList(buttonList.stream()
                            .filter(btn -> btn.getParentId().equals(zPermissionDTO.getPermissionId()))
                            .map(btnPerm -> {
                                ZRolePermissionDTO btnPermDTO = rolePermissionConvert.entityToDto(btnPerm);
                                // 按钮权限
                                if (rolePermissionList != null && rolePermissionList.contains(btnPerm.getPermissionId())) {
                                    // 有权限
                                    btnPermDTO.setHasPermission(true);
                                } else {
                                    // 无权限
                                    btnPermDTO.setHasPermission(false);
                                }
                                return btnPermDTO;
                            })
                            .collect(Collectors.toList()));
                    // 子菜单权限
                    if (rolePermissionList != null && rolePermissionList.contains(zPermissionDTO.getPermissionId())) {
                        // 有权限
                        zPermissionDTO.setHasPermission(true);
                    } else {
                        // 无权限
                        zPermissionDTO.setHasPermission(false);
                    }
                    // 迭代查询子菜单
                    List<ZRolePermissionDTO> childrenList = rolePermissionChildren(zPermissions,
                            buttonList, rolePermissionList, zPermissionDTO.getPermissionId());
                    if (childrenList != null && childrenList.size() > 0) {
                        zPermissionDTO.setChildren(childrenList);
                    }
                    return zPermissionDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ZPermission> getListById(String permissionId) {
        return permissionMapper.getListById(permissionId);
    }
}

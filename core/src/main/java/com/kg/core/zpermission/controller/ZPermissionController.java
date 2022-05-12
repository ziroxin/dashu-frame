package com.kg.core.zpermission.controller;


import com.kg.core.security.util.CurrentUserUtils;
import com.kg.core.zpermission.entity.ZPermission;
import com.kg.core.zpermission.service.IZPermissionService;
import com.kg.core.zuser.entity.ZUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 资源权限表 前端控制器
 * </p>
 *
 * @author ziro
 * @since 2022-05-09
 */
@Api(tags = "ZPermissionController", value = "资源权限信息", description = "资源权限信息")
@RestController
@RequestMapping("permission")
public class ZPermissionController {

    @Autowired
    private IZPermissionService permissionService;

    @ApiOperation(value = "permission/add", notes = "添加资源（菜单，按钮等）", httpMethod = "POST")
    @ApiImplicitParams({
    })
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('permission:add')")
    public boolean add(ZPermission zPermission) {
        if (permissionService.save(zPermission)) {
            return true;
        }
        return false;
    }

    @ApiOperation(value = "permission/update", notes = "修改资源（菜单，按钮等）", httpMethod = "POST")
    @ApiImplicitParams({
    })
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('permission:update')")
    public boolean update(ZPermission zPermission) {
        if (permissionService.updateById(zPermission)) {
            return true;
        }
        return false;
    }

    @ApiOperation(value = "permission/delete", notes = "删除资源（菜单，按钮等）", httpMethod = "DELETE")
    @ApiImplicitParams({
    })
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('permission:delete')")
    public boolean delete(String[] permissionIds) {
        if (permissionService.removeBatchByIds(Arrays.asList(permissionIds))) {
            return true;
        }
        return false;
    }

    @ApiOperation(value = "permission/user/all", notes = "查询当前用户所有资源权限", httpMethod = "GET")
    @GetMapping("user/all")
    @PreAuthorize("hasAuthority('permission:user:all')")
    public Map<String, Object> userAll() {
        Map<String, Object> map = new HashMap<>();
        ZUser currentUser = CurrentUserUtils.getCurrentUser();
        map.put("user", currentUser);
        map.putAll(permissionService.listPermissionByUserId(currentUser.getUserId()));
        return map;
    }


}

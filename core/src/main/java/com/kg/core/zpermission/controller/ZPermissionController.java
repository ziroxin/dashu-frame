package com.kg.core.zpermission.controller;


import com.kg.core.security.util.CurrentUserUtils;
import com.kg.core.zpermission.service.IZPermissionService;
import com.kg.core.zuser.entity.ZUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "permission/user/all", notes = "查询当前用户所有资源权限", httpMethod = "GET")
    @GetMapping("user/all")
    public Map<String, Object> userAll() {
        Map<String, Object> map = new HashMap<>();
        ZUser currentUser = CurrentUserUtils.getCurrentUser();
        map.put("user", currentUser);
        map.putAll(permissionService.listPermissionByUserId(currentUser.getUserId()));
        return map;
    }


}

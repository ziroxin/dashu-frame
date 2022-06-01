package com.kg.core.zuser.controller;


import com.kg.core.exception.BaseException;
import com.kg.core.zuser.dto.ZUserRoleSaveDTO;
import com.kg.core.zuser.service.IZUserRoleService;
import com.kg.core.zuser.service.IZUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author ziro
 * @since 2022-05-01
 */
@Api(tags = "ZUserController", value = "用户信息", description = "用户信息")
@RestController
@RequestMapping("user")
public class ZUserController {

    @Autowired
    private IZUserService userService;
    @Autowired
    private IZUserRoleService userRoleService;

    @ApiOperation(value = "user/list", notes = "查询用户列表", httpMethod = "GET")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user:list')")
    public List<ZUserRoleSaveDTO> list() {
        return userService.getUserRoleList();
    }


    @ApiOperation(value = "user/add", notes = "添加用户", httpMethod = "POST")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('user:add')")
    public void add(@RequestBody ZUserRoleSaveDTO zUserRoleSaveDTO) throws BaseException {
        boolean s1 = userService.add(zUserRoleSaveDTO);
        if (s1) {
            throw new BaseException("添加用户失败!");
        }
    }

    @ApiOperation(value = "user/update", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('user:update')")
    public void update(@RequestBody ZUserRoleSaveDTO zUserRoleSaveDTO) throws BaseException {
        boolean s1 = userService.update(zUserRoleSaveDTO);
        if (s1) {
            throw new BaseException("修改用户信息失败");
        }
    }

    @ApiOperation(value = "user/delete", notes = "批量删除用户", httpMethod = "DELETE")
    @DeleteMapping("delete")
    @PreAuthorize("hasAuthority('user:delete')")
    public void delete(@RequestBody String[] userIds) throws BaseException {
        boolean s1 = userService.removeByIds(Arrays.asList(userIds));
        boolean s2 = userRoleService.removeByIds(Arrays.asList(userIds));
        if (!s1 && !s2) {
            throw new BaseException("删除用户失败！");
        }
    }
}

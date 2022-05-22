package com.kg.core.zrole.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kg.core.exception.BaseException;
import com.kg.core.zrole.entity.ZRole;
import com.kg.core.zrole.service.IZRoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author ziro
 * @since 2022-05-21
 */
@RestController
@RequestMapping("role")
public class ZRoleController {

    @Autowired
    private IZRoleService roleService;

    @ApiOperation(value = "role/list", notes = "查询角色列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "query", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query", required = true, dataType = "Integer")
    })
    @GetMapping("list")
    @PreAuthorize("hasAuthority('role:list')")
    public Page<ZRole> list(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        Page<ZRole> pager = new Page<>(page, limit);
        QueryWrapper<ZRole> orderWrapper = new QueryWrapper<>();
        orderWrapper.lambda().orderByAsc(ZRole::getRoleOrder);
        return roleService.page(pager, orderWrapper);
    }

    @ApiOperation(value = "role/add", notes = "添加角色", httpMethod = "POST")
    @PostMapping("add")
    @PreAuthorize("hasAuthority('role:add')")
    public void add(@RequestBody ZRole zRole) throws BaseException {
        zRole.setCreateTime(LocalDateTime.now());
        if (!roleService.save(zRole)) {
            throw new BaseException("添加角色失败");
        }
    }

    @ApiOperation(value = "role/update", notes = "修改角色", httpMethod = "POST")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('role:update')")
    public void update(@RequestBody ZRole zRole) throws BaseException {
        zRole.setUpdateTime(LocalDateTime.now());
        if (!roleService.updateById(zRole)) {
            throw new BaseException("修改角色失败");
        }
    }

    @ApiOperation(value = "role/delete", notes = "批量删除角色", httpMethod = "DELETE")
    @DeleteMapping("delete")
    @PreAuthorize("hasAuthority('role:delete')")
    public void delete(@RequestBody String[] roleIds) throws BaseException {
        if (!roleService.removeByIds(Arrays.asList(roleIds))) {
            throw new BaseException("删除角色失败");
        }
    }
}

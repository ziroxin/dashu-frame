package com.kg.core.zuser.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kg.component.utils.GuidUtils;
import com.kg.core.exception.BaseException;
import com.kg.core.zuser.entity.ZUser;
import com.kg.core.zuser.service.IZUserService;
import io.swagger.annotations.Api;
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

    @ApiOperation(value = "user/list", notes = "查询用户列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "query", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query", required = true, dataType = "Integer")
    })
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user:list')")
    public Page<ZUser> list(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        Page<ZUser> pager = new Page<>(page, limit);
        return userService.page(pager);
    }

    @ApiOperation(value = "user/add", notes = "添加用户", httpMethod = "POST")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('user:add')")
    public void add(@RequestBody ZUser zUser) throws BaseException {
        zUser.setUserId(GuidUtils.getUuid());
        zUser.setCreateTime(LocalDateTime.now());
        if (!userService.save(zUser)) {
            throw new BaseException("添加用户失败!");
        }
    }

    @ApiOperation(value = "user/update", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('user:update')")
    public void update(@RequestBody ZUser zUser) throws BaseException {
        zUser.setUpdateTime(LocalDateTime.now());
        if (!userService.updateById(zUser)) {
            throw new BaseException("修改用户信息失败");
        }
    }

    @ApiOperation(value = "user/delete", notes = "批量删除用户", httpMethod = "DELETE")
    @DeleteMapping("delete")
    @PreAuthorize("hasAuthority('user:delete')")
    public void delete(@RequestBody String[] userIds) throws BaseException {
        if (!userService.removeByIds(Arrays.asList(userIds))) {
            throw new BaseException("删除用户失败！");
        }
    }
}

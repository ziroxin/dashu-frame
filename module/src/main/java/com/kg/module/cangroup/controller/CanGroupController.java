package com.kg.module.cangroup.controller;


import com.kg.component.utils.GuidUtils;
import com.kg.module.cangroup.entity.CanGroup;
import com.kg.module.cangroup.service.ICanGroupService;
import com.kg.module.canshop.entity.CanUserShop;
import com.kg.core.exception.BaseException;
import com.kg.core.security.util.CurrentUserUtils;
import com.kg.core.zuser.entity.ZUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


/**
 * <p>
 * 菜品分组表 前端控制器
 * </p>
 *
 * @author dazui
 * @since 2022-06-24
 */
@Api(tags = "CanGroupController", value = "菜品分组信息", description = "菜品分组信息")
@RestController
@RequestMapping("group")
public class CanGroupController {

    @Autowired
    private ICanGroupService canGroupService;

    @ApiOperation(value = "group/list", notes = "查询菜品分组信息", httpMethod = "GET")
    @ApiImplicitParams({})
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('group:list')")
    public List<CanGroup> List() {
        return canGroupService.list();
    }

    @ApiOperation(value = "group/getGroupById", notes = "查询本店铺菜品分组信息", httpMethod = "GET")
    @ApiImplicitParams({})
    @GetMapping("/getGroupById")
    @PreAuthorize("hasAuthority('group:list')")
    public List<CanGroup> getGroupById() {
        ZUser user = CurrentUserUtils.getCurrentUser();
        CanUserShop usershop = canGroupService.getUserShop(user.getUserId());
        return canGroupService.getGroupById(usershop.getShopId());
    }

    @ApiOperation(value = "group/add", notes = "添加店铺", httpMethod = "POST")
    @ApiImplicitParams({})
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('group:add')")
    public boolean add(@RequestBody CanGroup canGroup) throws BaseException {
        ZUser user = CurrentUserUtils.getCurrentUser();
        CanUserShop usershop = canGroupService.getUserShop(user.getUserId());
        canGroup.setShopId(usershop.getShopId());
        canGroup.setGroupId(GuidUtils.getUuid());
        canGroup.setCreateTime(LocalDateTime.now());
        if (canGroupService.save(canGroup)) {
            return true;
        }
        return false;
    }

    @ApiOperation(value = "group/update", notes = "修改分组信息", httpMethod = "POST")
    @ApiImplicitParams({})
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('group:update')")
    public boolean update(@RequestBody CanGroup canGroup) throws BaseException {

        canGroup.setUpdateTime(LocalDateTime.now());
        if (canGroupService.updateById(canGroup)) {
            return true;
        }
        return false;
    }

    @ApiOperation(value = "group/delete", notes = "删除分组", httpMethod = "DELETE")
    @ApiImplicitParams({})
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('group:delete')")
    public boolean delete(@RequestBody String[] groupIds) {
        if (canGroupService.removeBatchByIds(Arrays.asList(groupIds))) {
            return true;
        }
        return false;
    }
}

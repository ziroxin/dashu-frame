package com.kg.core.canshop.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kg.component.utils.GuidUtils;
import com.kg.core.canshop.entity.CanShop;
import com.kg.core.canshop.entity.CanUserShop;
import com.kg.core.canshop.service.ICanShopService;
import com.kg.core.canshop.service.ICanUserShopService;
import com.kg.core.exception.BaseException;
import com.kg.core.security.util.CurrentUserUtils;
import com.kg.core.zpermission.dto.ZPermissionDTO;
import com.kg.core.zpermission.entity.ZPermission;
import com.kg.core.zpermission.service.IZPermissionService;
import com.kg.core.zuser.entity.ZUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 店铺表 前端控制器
 * </p>
 *
 * @author dazui
 * @since 2022-06-23
 */
@Api(tags = "CanShopController", value = "店铺信息", description = "店铺信息")
@RestController
@RequestMapping("shop")
public class CanShopController {

    @Autowired
    private ICanShopService canShopService;

    @Autowired
    private ICanUserShopService canUserShopService;

    @ApiOperation(value = "shop/add",notes = "添加店铺",httpMethod = "POST")
    @ApiImplicitParams({})
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('shop:add')")
    public void add(@RequestBody CanShop canShop) throws BaseException{

        canShop.setShopId(GuidUtils.getUuid());
        canShop.setCreateTime(LocalDateTime.now());
        boolean s1 = canShopService.save(canShop);

//        获取当前登录用户信息
        ZUser user = CurrentUserUtils.getCurrentUser();
        CanUserShop canUserShop = new CanUserShop();
        canUserShop.setUserId(user.getUserId());
        canUserShop.setShopId(canShop.getShopId());
        boolean s2 = canUserShopService.save(canUserShop);
        if (!s1 && !s2) {
            throw new BaseException("添加店铺失败！");
        }
    }

    @ApiOperation(value = "shop/update", notes = "修改店铺信息", httpMethod = "POST")
    @ApiImplicitParams({})
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('shop:update')")
    public boolean update(@RequestBody CanShop canShop) throws BaseException {

        canShop.setUpdateTime(LocalDateTime.now());
        if (canShopService.updateById(canShop)) {
            return true;
        }
        return false;
    }

    @ApiOperation(value = "shop/delete", notes = "删除店铺", httpMethod = "DELETE")
    @ApiImplicitParams({})
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('shop:delete')")
    public void delete(@RequestBody String[] shopIds) throws BaseException {
        boolean s1 = canShopService.removeByIds(Arrays.asList(shopIds));
        boolean s2 = canUserShopService.removeByIds(Arrays.asList(shopIds));
        if (!s1 && !s2) {
            throw new BaseException("删除用户失败！");
        }
    }

    @ApiOperation(value = "shop/list", notes = "查询店铺资源", httpMethod = "GET")
    @ApiImplicitParams({})
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('shop:list')")
    public List<CanShop> List() {
        return canShopService.list();
    }
}

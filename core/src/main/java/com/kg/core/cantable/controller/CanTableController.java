package com.kg.core.cantable.controller;


import com.kg.component.utils.GuidUtils;
import com.kg.core.cangroup.entity.CanGroup;
import com.kg.core.cangroup.service.ICanGroupService;
import com.kg.core.canshop.entity.CanUserShop;
import com.kg.core.cantable.entity.CanTable;
import com.kg.core.cantable.service.ICanTableService;
import com.kg.core.exception.BaseException;
import com.kg.core.security.util.CurrentUserUtils;
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
 * 餐桌表 前端控制器
 * </p>
 *
 * @author dazui
 * @since 2022-06-27
 */
@Api(tags = "CanTableController", value = "餐桌信息", description = "餐桌信息")
@RestController
@RequestMapping("table")
public class CanTableController {

    @Autowired
    private ICanTableService canTableService;

    @Autowired
    private ICanGroupService canGroupService;

    @ApiOperation(value = "table/list", notes = "查询餐桌信息", httpMethod = "GET")
    @ApiImplicitParams({})
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('table:list')")
    public List<CanTable> List() {
        return canTableService.list();
    }


    @ApiOperation(value = "table/add", notes = "添加餐桌信息", httpMethod = "POST")
    @ApiImplicitParams({})
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('table:add')")
    public boolean add(@RequestBody CanTable canTable) throws BaseException {
        ZUser user = CurrentUserUtils.getCurrentUser();
        CanUserShop usershop = canGroupService.getUserShop(user.getUserId());
        canTable.setShopId(usershop.getShopId());
        canTable.setTableId(GuidUtils.getUuid());
        canTable.setCreateTime(LocalDateTime.now());
        if (canTableService.save(canTable)) {
            return true;
        }
        return false;
    }


    @ApiOperation(value = "table/update", notes = "修改餐桌信息", httpMethod = "POST")
    @ApiImplicitParams({})
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('table:update')")
    public boolean update(@RequestBody CanTable canTable) throws BaseException {
        canTable.setUpdateTime(LocalDateTime.now());
        if (canTableService.updateById(canTable)) {
            return true;
        }
        return false;
    }


    @ApiOperation(value = "table/delete", notes = "删除餐桌信息", httpMethod = "DELETE")
    @ApiImplicitParams({})
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('table:delete')")
    public boolean delete(@RequestBody String[] tableIds) {
        if (canTableService.removeBatchByIds(Arrays.asList(tableIds))) {
            return true;
        }
        return false;
    }
}

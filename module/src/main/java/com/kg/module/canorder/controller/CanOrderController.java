package com.kg.module.canorder.controller;


import com.kg.component.utils.GuidUtils;
import com.kg.module.cangroup.service.ICanGroupService;
import com.kg.module.canorder.entity.CanOrder;
import com.kg.module.canorder.service.ICanOrderService;
import com.kg.core.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author dazui
 * @since 2022-06-27
 */
@Api(tags = "CanOrderController", value = "订单信息", description = "订单信息")
@RestController
@RequestMapping("order")
public class CanOrderController {

    @Autowired
    private ICanOrderService canOrderService;

    @Autowired
    private ICanGroupService canGroupService;

    @ApiOperation(value = "order/list", notes = "查询订单信息", httpMethod = "GET")
    @ApiImplicitParams({})
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('order:list')")
    public List<CanOrder> List() {
        return canOrderService.list();
    }


    @ApiOperation(value = "order/add", notes = "添加订单", httpMethod = "POST")
    @ApiImplicitParams({})
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('order:add')")
    public boolean add(@RequestBody CanOrder canOrder) throws BaseException {
        canOrder.setOrderId(GuidUtils.getUuid());
        canOrder.setCreateTime(LocalDateTime.now());
        if (canOrderService.save(canOrder)) {
            return true;
        }
        return false;
    }

}

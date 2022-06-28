package com.kg.module.canorder.controller;


import com.kg.module.canorder.entity.CanOrderDetails;
import com.kg.module.canorder.service.ICanOrderDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单详情表 前端控制器
 * </p>
 *
 * @author dazui
 * @since 2022-06-28
 */
@Api(tags = "CanOrderDetailsController", value = "订单详情信息", description = "订单详情信息")
@RestController
@RequestMapping("orderDetails")
public class CanOrderDetailsController {

    @Autowired
    private ICanOrderDetailsService canOrderDetailsService;

    @ApiOperation(value = "orderDetails/getById", notes = "查询订单详情信息", httpMethod = "GET")
    @ApiImplicitParams({})
    @GetMapping("/getById")
    @PreAuthorize("hasAuthority('orderDetails:getById')")
    public CanOrderDetails List(String orderId) {
        return canOrderDetailsService.getById(orderId);
    }
}

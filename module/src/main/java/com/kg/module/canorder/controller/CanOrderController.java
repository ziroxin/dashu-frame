package com.kg.module.canorder.controller;


import com.kg.component.utils.GuidUtils;
import com.kg.core.exception.BaseException;
import com.kg.module.cangroup.service.ICanGroupService;
import com.kg.module.canorder.entity.CanOrder;
import com.kg.module.canorder.service.ICanOrderBillService;
import com.kg.module.canorder.service.ICanOrderDetailsService;
import com.kg.module.canorder.service.ICanOrderService;
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
    private ICanOrderBillService canOrderBillService;

    @Autowired
    private ICanOrderDetailsService canOrderDetailsService;

    @Autowired
    private ICanGroupService canGroupService;


    @ApiOperation(value = "order/list", notes = "查询订单信息", httpMethod = "GET")
    @ApiImplicitParams({})
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('order:list')")
    public List<CanOrder> list(@RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime) {
        return canOrderService.shopListByTime(startTime, endTime);
    }


    @ApiOperation(value = "order/add", notes = "添加订单", httpMethod = "POST")
    @ApiImplicitParams({})
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('order:add')")
    public boolean add(@RequestBody CanOrder canOrder) throws BaseException {
        canOrder.setOrderId(GuidUtils.getUuid());
        canOrder.setCreateTime(LocalDateTime.now());
        canOrder.setDiningTime(canOrder.getCreateTime());
        if (canOrderService.save(canOrder)) {
            return true;
        }
        return false;
    }


    @ApiOperation(value = "order/update", notes = "修改订单信息", httpMethod = "POST")
    @ApiImplicitParams({})
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('order:update')")
    public boolean update(@RequestBody CanOrder canOrder) throws BaseException {
        canOrder.setUpdateTime(LocalDateTime.now());
        if (canOrderService.updateById(canOrder)) {
            return true;
        }
        return false;
    }

    @ApiOperation(value = "order/delete", notes = "删除订单信息", httpMethod = "DELETE")
    @ApiImplicitParams({})
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('order:delete')")
    public void delete(@RequestBody String[] orderIds) throws BaseException {

        boolean s1 = canOrderService.removeBatchByIds(Arrays.asList(orderIds));
        boolean s2 = canOrderBillService.deleteBill(Arrays.asList(orderIds));
        boolean s3 = canOrderDetailsService.deleteDetails(Arrays.asList(orderIds));
        if (!s1 & !s2 & !s3) {
            throw new BaseException("删除订单失败！");
        }
    }
}

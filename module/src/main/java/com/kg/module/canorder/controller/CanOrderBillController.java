package com.kg.module.canorder.controller;


import com.kg.component.utils.GuidUtils;
import com.kg.core.exception.BaseException;
import com.kg.module.canorder.entity.CanOrderBill;
import com.kg.module.canorder.service.ICanOrderBillService;
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
 * 发票表 前端控制器
 * </p>
 *
 * @author dazui
 * @since 2022-06-28
 */
@Api(tags = "CanOrderBillController", value = "订单发票信息", description = "订单发票信息")
@RestController
@RequestMapping("orderbill")
public class CanOrderBillController {

    @Autowired
    private ICanOrderBillService canOrderBillService;

    @ApiOperation(value = "orderbill/getById", notes = "查询订单发票信息", httpMethod = "GET")
    @ApiImplicitParams({})
    @GetMapping("/getOrderBillById")
    @PreAuthorize("hasAuthority('orderbill:getById')")
    public CanOrderBill getOrderBillById(String orderId) {
        return canOrderBillService.getOrderBillById(orderId);
    }


    @ApiOperation(value = "orderbill/list", notes = "查询发票信息", httpMethod = "GET")
    @ApiImplicitParams({})
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('orderbill:list')")
    public List<CanOrderBill> List() {
        return canOrderBillService.list();
    }


    @ApiOperation(value = "orderbill/add", notes = "添加发票信息", httpMethod = "POST")
    @ApiImplicitParams({})
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('orderbill:add')")
    public boolean add(@RequestBody CanOrderBill canOrderBill) throws BaseException {
        canOrderBill.setOrderBillId(GuidUtils.getUuid());
        canOrderBill.setCreateTime(LocalDateTime.now());
        if (canOrderBillService.save(canOrderBill)) {
            return true;
        }
        return false;
    }

    @ApiOperation(value = "orderbill/update", notes = "修改发票信息", httpMethod = "POST")
    @ApiImplicitParams({})
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('orderbill:update')")
    public boolean update(@RequestBody CanOrderBill canOrderBill) throws BaseException {
        canOrderBill.setUpdateTime(LocalDateTime.now());
        if (canOrderBillService.updateById(canOrderBill)) {
            return true;
        }
        return false;
    }

    @ApiOperation(value = "orderbill/delete", notes = "删除发票信息", httpMethod = "DELETE")
    @ApiImplicitParams({})
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('orderbill:delete')")
    public boolean delete(@RequestBody String[] orderBillIds) {
        if (canOrderBillService.removeBatchByIds(Arrays.asList(orderBillIds))) {
            return true;
        }
        return false;
    }
}

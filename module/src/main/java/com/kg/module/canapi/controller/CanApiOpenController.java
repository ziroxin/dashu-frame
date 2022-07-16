package com.kg.module.canapi.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kg.component.utils.WxGetOpenIdUtils;
import com.kg.core.exception.BaseException;
import com.kg.module.canapi.dto.CanGroupListDTO;
import com.kg.module.candishes.dto.CanDishesGroupDTO;
import com.kg.module.candishes.entity.CanDishes;
import com.kg.module.candishes.service.ICanDishesService;
import com.kg.module.cangroup.entity.CanGroup;
import com.kg.module.cangroup.service.ICanGroupService;
import com.kg.module.canorder.dto.CanOrderDTO;
import com.kg.module.canorder.service.ICanOrderService;
import com.kg.module.canshop.entity.CanShop;
import com.kg.module.canshop.service.ICanShopService;
import com.kg.module.cantable.entity.CanTable;
import com.kg.module.cantable.service.ICanTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 点餐小程序公开API
 *
 * @author ziro
 * @date 2022-06-28 21:52:24
 */
@Api(tags = "CanApiOpenController", value = "点餐小程序公开API", description = "点餐小程序公开API")
@RestController
@RequestMapping("/can/api/open")
public class CanApiOpenController {
    @Autowired
    private ICanShopService shopService;
    @Autowired
    private ICanTableService tableService;
    @Autowired
    private ICanDishesService dishesService;
    @Autowired
    private ICanGroupService groupService;
    @Autowired
    private ICanOrderService orderService;

    @ApiOperation(value = "wkrj/interface/wxToken", notes = "微信登录", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jsCode", value = "微信登录code", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("wxToken")
    public String wxToken(String jsCode) throws BaseException {
        String openId = WxGetOpenIdUtils.getOpenId(jsCode);
        if (!StringUtils.hasText(openId)) {
            throw new BaseException("获取openid失败");
        }
        return openId;
    }

    @ApiOperation(value = "can/api/open/shop/info", notes = "查询店铺详细信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "店铺ID", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("/shop/info")
    public CanShop getShopInfo(String shopId) {
        return shopService.getById(shopId);
    }

    @ApiOperation(value = "can/api/open/table/info", notes = "查询餐桌详细信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "店铺ID", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "tableId", value = "餐桌ID", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("/table/info")
    public CanTable getTableInfo(String shopId, String tableId) {
        QueryWrapper<CanTable> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CanTable::getShopId, shopId)
                .eq(CanTable::getTableId, tableId);
        return tableService.getOne(wrapper);
    }

    @ApiOperation(value = "can/api/open/table/list", notes = "查询餐桌列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "店铺ID", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("/table/list")
    public List<CanTable> getTableList(String shopId) {
        QueryWrapper<CanTable> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CanTable::getShopId, shopId)
                .orderByAsc(CanTable::getCreateTime);
        return tableService.list(wrapper);
    }

    @ApiOperation(value = "can/api/open/dishes/searchList", notes = "根据名称模糊查询菜品", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "店铺ID", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "dishesName", value = "菜品名称", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("/dishes/searchList")
    public List<CanDishes> getSearchList(String shopId, String dishesName) {
        QueryWrapper<CanDishes> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CanDishes::getShopId, shopId)
                .like(CanDishes::getDishesName, dishesName)
                .orderByAsc(CanDishes::getDishesOrder);
        return dishesService.list(wrapper);
    }

    @ApiOperation(value = "can/api/open/dishes/list", notes = "分组查询菜品", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "店铺ID", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("/dishes/list")
    public List<CanGroupListDTO> getDishesList(String shopId) {
        // 查询所有分组
        QueryWrapper<CanGroup> groupWrapper = new QueryWrapper();
        groupWrapper.lambda().eq(CanGroup::getShopId, shopId)
                .orderByAsc(CanGroup::getGroupOrder);
        List<CanGroup> groupList = groupService.list(groupWrapper);
        // 查询所有（带分组）菜品
        List<CanDishesGroupDTO> dishesGroupList = dishesService.getDishesGroupList(shopId);
        // 组装分组和菜品
        List<CanGroupListDTO> result = groupList.stream().map(canGroup -> {
            CanGroupListDTO groupListDTO = JSONUtil.toBean(JSONUtil.parseObj(canGroup), CanGroupListDTO.class);
            List<CanDishesGroupDTO> dishesList = dishesGroupList.stream()
                    .filter(dishes -> dishes.getGroupId().equals(canGroup.getGroupId())).collect(Collectors.toList());
            groupListDTO.setDishes(dishesList);
            return groupListDTO;
        }).collect(Collectors.toList());
        return result;
    }

    @ApiOperation(value = "can/api/open/order/save", notes = "订单保存", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "canOrderDTO", value = "订单信息", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("order/save")
    public void saveOrder(@RequestBody CanOrderDTO canOrderDTO) {
        // 保存订单信息
        orderService.saveOrder(canOrderDTO);
    }

}

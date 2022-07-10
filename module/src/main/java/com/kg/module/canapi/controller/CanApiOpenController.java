package com.kg.module.canapi.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kg.component.utils.WxGetOpenIdUtils;
import com.kg.core.exception.BaseException;
import com.kg.module.canapi.dto.CanGroupListDTO;
import com.kg.module.candishes.dto.CanDishesGroupDTO;
import com.kg.module.candishes.service.ICanDishesService;
import com.kg.module.cangroup.entity.CanGroup;
import com.kg.module.cangroup.service.ICanGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ICanDishesService dishesService;
    @Autowired
    private ICanGroupService groupService;

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

    /**
     * 分组查询菜品
     */
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

}

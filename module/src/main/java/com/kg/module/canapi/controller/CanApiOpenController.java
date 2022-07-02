package com.kg.module.canapi.controller;

import com.kg.module.candishes.dto.CanDishesGroupDTO;
import com.kg.module.candishes.service.ICanDishesService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/dishes/list")
    public List<CanDishesGroupDTO> getDishesList(String shopId) {
        return dishesService.getDishesGroupList(shopId);
    }

}
